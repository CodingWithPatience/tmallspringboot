package com.zhihao.tmall.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihao.tmall.mapper.ProductImageMapper;
import com.zhihao.tmall.pojo.ProductImage;
import com.zhihao.tmall.pojo.ProductImageExample;
import com.zhihao.tmall.service.ProductImageService;
import com.zhihao.tmall.util.ImageUtil;
import com.zhihao.tmall.util.UploadedImageFile;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	ProductImageMapper productImageMapper;
	
	@Override
	public void add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) {
		productImageMapper.insert(pi);
		String fileName = pi.getId()+ ".jpg";
		String imageFolder;
		String imageFolder_small=null;
		String imageFolder_middle=null;
		if(ProductImageService.type_single.equals(pi.getType())){
			imageFolder= session.getServletContext().getRealPath("img/productSingle");
			imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
			imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
		}
		else{
			imageFolder= session.getServletContext().getRealPath("img/productDetail");
		}

		File f = new File(imageFolder, fileName);
		f.getParentFile().mkdirs();
		try {
			uploadedImageFile.getImage().transferTo(f);
			BufferedImage img = ImageUtil.change2jpg(f);
			ImageIO.write(img, "jpg", f);

			if(ProductImageService.type_single.equals(pi.getType())) {
				File f_small = new File(imageFolder_small, fileName);
				File f_middle = new File(imageFolder_middle, fileName);

				ImageUtil.resizeImage(f, 56, 56, f_small);
				ImageUtil.resizeImage(f, 217, 190, f_middle);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, HttpSession session) {
		ProductImage pi = get(id);
		productImageMapper.deleteByPrimaryKey(id);
		String fileName = pi.getId()+ ".jpg";
		String imageFolder;
		String imageFolder_small=null;
		String imageFolder_middle=null;

		if(ProductImageService.type_single.equals(pi.getType())){
			imageFolder= session.getServletContext().getRealPath("img/productSingle");
			imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
			imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
			File imageFile = new File(imageFolder,fileName);
			File f_small = new File(imageFolder_small,fileName);
			File f_middle = new File(imageFolder_middle,fileName);
			imageFile.delete();
			f_small.delete();
			f_middle.delete();
		}
		else{
			imageFolder= session.getServletContext().getRealPath("img/productDetail");
			File imageFile = new File(imageFolder,fileName);
			imageFile.delete();
		}
	}

	@Override
	public void update(ProductImage pi) {
		productImageMapper.updateByPrimaryKeySelective(pi);

	}

	@Override
	public ProductImage get(int id) {
		return productImageMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProductImage> list(int pid, String type) {
		ProductImageExample example = new ProductImageExample();
		example.createCriteria()
						.andPidEqualTo(pid)
						.andTypeEqualTo(type);
		example.setOrderByClause("id desc");
		return productImageMapper.selectByExample(example);
	}
}
