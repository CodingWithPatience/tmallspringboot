package com.zhihao.tmall.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihao.tmall.mapper.ProductMapper;
import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.pojo.ProductExample;
import com.zhihao.tmall.pojo.ProductImage;
import com.zhihao.tmall.service.CategoryService;
import com.zhihao.tmall.service.OrderItemService;
import com.zhihao.tmall.service.ProductImageService;
import com.zhihao.tmall.service.ProductService;
import com.zhihao.tmall.service.ReviewService;
import com.zhihao.tmall.util.Page;

@Service
public class ProductServiceImpl implements ProductService {

	private static final String REVIEW = "review";
	private static final String SALE = "saleCount";

	@Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
	OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    
    // 获取某一分类下的产品的总数
	@Override
	public long getTotal(int cid) {
		return productMapper.getTotal(cid);
	}

	// 添加产品 @Override
	public void add(Product p) {
		productMapper.insert(p);
	}

	// 删除产品
	@Override
	public void delete(int id) {
		productMapper.deleteByPrimaryKey(id);
	}

	// 更新产品
	@Override
	public void update(Product p) {
		productMapper.updateByPrimaryKeySelective(p);
	}

	// 通过产品id获取产品
	@Override
	public Product get(Integer id) {
		Product p = productMapper.selectByPrimaryKey(id);
		setCategory(p);                // 设置产品所属分类
		setFirstProductImage(p);       // 设置产品的第一张图片
		setSaleAndReviewNumber(p);     // 设置产品的销售和评价数量
		
		// 获取用于展示产品的图片，并关联图片
        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
		return p;
	}

	// 通过循环调用setCategory()方法批量设置产品所属分类
	public void setCategory(List<Product> ps){
		for (Product p : ps)
			setCategory(p);
	}
	
	// 设置单个产品所属分类
	public void setCategory(Product p){
		int cid = p.getCid();
		Category c = categoryService.get(cid);
		p.setCategory(c);
	}
	
	// 获取ProductExample实例， ProductExample用于自定义查询条件
	public ProductExample getProductExample(int cid) {
		ProductExample example = new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		return example;
	}
	
	// 根据ProductExample参数来查询产品
	public List<Product> selectByExample(ProductExample example) {
		List<Product> result = productMapper.selectByExample(example);
		setCategory(result);           // 关联产品的分类           
		setFirstProductImage(result);  // 设置用于在主页中展示产品图片
		return result;
	}
	
	// 根据分类id查找产品，使用id递降的默认的排序方式
	@Override
	public List<Product> list(int cid) {
		ProductExample example = getProductExample(cid);
		return selectByExample(example);
	}

	// 通过分类id查询该分类下的所有产品，设置排序方式，对结果集进行排序
	@Override
	public List<Product> list(int cid, String sortCondition) {
		ProductExample example = getProductExample(cid);
		example.setOrderByClause(sortCondition);
		return selectByExample(example);
	}
	
	// 按照参数sort进行排序再返回结果，若sort为REVIEW或SALE
	// 需用到连结查询，再对返回的结果进行排序
	@Override
	public List<Product> sortedlist(int cid, String sortCondition) {
		if(sortCondition.equals(REVIEW) || sortCondition.equals(SALE)) {
			List<Product> result = new ArrayList<>(); 
			if(sortCondition.equals(REVIEW))
				result = productMapper.sortByReview(cid); 
			else
				result = productMapper.sortBySale(cid);

			setCategory(result);           // 关联产品的分类           
			setFirstProductImage(result);  // 设置用于在主页中展示产品图片
			return result;
		}
		else {
			return list(cid, sortCondition);
		}
	}

	// 以分页的方式查询产品
	@Override
	public List<Product> list(int cid, Page page) {
		ProductExample example = getProductExample(cid);
		example.setOffset(page.getStart());       // 设置查询产品返回结果中的第一条数据所在位置
		example.setLimit(page.getCount());        // 设置查询返回结果的数据数量
		return selectByExample(example);
	}

	// 设置用于在主页中展示产品的图片，该图片为该产品的第一张图片
	@Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }
	
	// 通过调用fill(Category c)批量将分类与产品关联
	@Override
	public void fill(List<Category> cs) {
		for (Category c : cs) {
		    fill(c);
		}
	}

	// 将单一分类与所属产品关联
	@Override
	public void fill(Category c) {
		List<Product> ps = list(c.getId());
		setFirstProductImage(ps);
		c.setProducts(ps);
	}
	
	// 将单一分类与所属产品关联，返回的产品结果集使用自定义排序方式排序
	@Override
	public void fill(Category c, String sort) {
		List<Product> ps = sortedlist(c.getId(), sort);
		setFirstProductImage(ps);
		c.setProducts(ps);
	}

	// 下面方法的批量处理方式
	@Override
	public void fillByRow(List<Category> cs) {
		for (Category c : cs) {
			fillByRow(c);
		}
	}
	
	// 该方法用于当用户鼠标进入分类的名称时，展示该分类所属产品的
	// 一些子标题集合，这些标题展示产品的特点，吸引用户的关注
	public void fillByRow(Category category) {
		int productNumberEachRow = 8;
		List<Product> products = category.getProducts();
		List<List<Product>> productsByRow = new ArrayList<>();
		for (int i = 0; i < products.size(); i+=productNumberEachRow) {
			int size = i+productNumberEachRow;
			size= size>products.size()?products.size():size;
			List<Product> productsOfEachRow = products.subList(i, size);
			productsByRow.add(productsOfEachRow);
		}
		category.setProductsByRow(productsByRow);
	}

	// 批量设置在home页中展示产品的图片
	@Override
	public void setFirstProductImage(List<Product> ps) {
		for (Product p : ps) {
		    setFirstProductImage(p);
		}
	}
	
	// 设置销售数量和评价数量
	@Override
    public void setSaleAndReviewNumber(Product p) {
		int saleCount = orderItemService.getSaleCount(p.getId());
        p.setSaleCount(saleCount);
 
        int reviewCount = reviewService.getCount(p.getId());
        p.setReviewCount(reviewCount);
    }
 
	// 上面方法的批量处理方式
    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setSaleAndReviewNumber(p);
        }
    }
    
    // 模糊查询，返回搜索结果
    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List<Product> result = productMapper.selectByExample(example);
        setFirstProductImage(result);
        setCategory(result);
        return result;
    }
    
    // 通过产品获取分类名称，用于智能搜索提示框，实时返回产品所属分类
    @Override
    public Set<String> getCategoryName(List<Product> products) {
    	List<String> cNames = new ArrayList<>();
    	for(Product product : products) {
    		cNames.add(product.getCategory().getName());
    	}
    	Set<String> result = new HashSet<>(cNames);
    	return result;
    }

	
}
