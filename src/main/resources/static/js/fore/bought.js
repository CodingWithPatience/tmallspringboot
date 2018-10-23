/**
 * 渲染订单 
 */

// 渲染table的第一个tr元素
function createTableHeadTr(data) {
	var headTr = document.createElement("tr");
	headTr.className = "orderListItemFirstTR";
	
	var td1 = document.createElement("td");
	td1.setAttribute("colspan", "2");

	var b = document.createElement("b");
	var date = data.createDate;
	date = (new Date(date)).getTime();
	date = new Date(date).format("yyyy-MM-dd hh:mm:ss");
	var createDate = document.createTextNode(date);
	b.appendChild(createDate);

	var span = document.createElement("span");
	var orderCode = document.createTextNode("订单号："+data.orderCode);
	span.appendChild(orderCode);

	td1.appendChild(b);
	td1.appendChild(span);
	headTr.appendChild(td1);
	
	var td2 = document.createElement("td");
	td2.setAttribute("colspan", "2");
	
	var img = document.createElement("img");
	img.setAttribute("width", "13px");
	img.setAttribute("src", getRootPath()+"img/site/orderItemTmall.png");
	var tmall = document.createTextNode("天猫商场");

	td2.appendChild(img);
	td2.appendChild(tmall);
	headTr.appendChild(td2);

	var td3 = document.createElement("td");
	td3.setAttribute("colspan", "1");

	var a = document.createElement("a");
	a.className = "wangwanglink";

	var span = document.createElement("span");
	span.className = "orderItemWangWangGif";

	a.appendChild(span);
	td3.appendChild(a);
	headTr.appendChild(td3);

	var td4 = document.createElement("td");
	td4.className = "orderItemDeleteTD";

	var a = document.createElement("a");
	a.className = "deleteOrderLink";
	a.setAttribute("onclick", "deleteOrderById()");
	a.setAttribute("oid", data.id);
	a.setAttribute("href", "#");

	var span = document.createElement("span");
	span.className = "orderListItemDelete glyphicon glyphicon-trash";

	a.appendChild(span);
	td4.appendChild(a);
	headTr.appendChild(td4);
	
	return headTr;
}

// 渲染包含订单信息的tr元素
function createTableProductTr(data, orderItem, i, length) {
	var productTr = document.createElement("tr");
	productTr.className = "orderItemProductInfoPartTR";

	// 产品图片td
	var td1 = document.createElement("td");
	td1.className = "orderItemProductInfoPartTD";

	var img = document.createElement("img");
	img.setAttribute("width","80px");
	img.setAttribute("height","80px");
	img.setAttribute("src",getRootPath()+"img/productSingle_middle/"+orderItem.product.firstProductImage.id+".jpg");

	td1.appendChild(img);
	productTr.appendChild(td1);
	
	// 产品详细信息td
	var td2 = document.createElement("td");
	td2.className = "orderItemProductInfoPartTD";

	var div = document.createElement("div");
	div.className = "orderItemProductLinkOutDiv";
	var a = document.createElement("a");
	a.setAttribute("href",getRootPath()+"product/"+orderItem.product.id);
	var pName = document.createTextNode(orderItem.product.name);
	a.appendChild(pName);
	div.appendChild(a);
	var div2 = document.createElement("div");
	div2.className = "orderItemProductLinkInnerDiv";
	var img1 = document.createElement("img");
	var img2 = document.createElement("img");
	var img3 = document.createElement("img");
	img1.setAttribute("src",getRootPath()+"img/site/creditcard.png");
	img1.setAttribute("title","支持信用卡支付");
	img2.setAttribute("src",getRootPath()+"img/site/7day.png");
	img2.setAttribute("title","消费者保障服务，承诺7天退货");
	img3.setAttribute("src",getRootPath()+"img/site/promise.png");
	img3.setAttribute("title","消费者保障服务，承诺如实描述");
	div2.appendChild(img1);
	div2.appendChild(img2);
	div2.appendChild(img3);
	div.appendChild(div2);
	td2.appendChild(div);
	productTr.appendChild(td2);

	// 产品原价和实价td
	var td3 = document.createElement("td");
	td3.className = "orderItemProductInfoPartTD";
	td3.setAttribute("width", "100px");
	td3.setAttribute("valign", "center");
	var div1 = document.createElement("div");
	var div2 = document.createElement("div");
	div1.style.textAlign="center";
	div2.style.textAlign="center";
	var formatOPrice = orderItem.product.originalPrice;
	var formatPPrice = orderItem.product.promotePrice;
	formatOPrice = formatMoney(formatOPrice);
	formatPPrice = formatMoney(formatPPrice);
	var oPrice = document.createTextNode(formatOPrice);
	var pPrice = document.createTextNode(formatPPrice);
	div1.appendChild(oPrice);
	div2.appendChild(pPrice);
	td3.appendChild(div1);
	td3.appendChild(div2);
	productTr.appendChild(td3);

	if(i == 0) {
		// 产品总数量td
		var td4 = document.createElement("td");
		td4.setAttribute("valign","center");
		td4.setAttribute("rowspan",length);
		td4.setAttribute("width","80px");
		td4.className = "orderListItemButtonTD orderItemOrderInfoPartTD";
		var span = document.createElement("span");
		span.className = "orderListItemNumber";
		var number = document.createTextNode(data.totalNumber);
		span.appendChild(number);
		td4.appendChild(span);
		productTr.appendChild(td4);

		// 产品总价td
		var td5 = document.createElement("td");
		td5.setAttribute("valign","center");
		td5.setAttribute("rowspan",length);
		td5.setAttribute("width","140px");
		td5.className = "orderListItemProductRealPriceTD orderItemOrderInfoPartTD";
		var div1 = document.createElement("div");
		var div2 = document.createElement("div");
		div1.className = "orderListItemProductRealPrice";
		div2.className = "orderListItemPriceWithTransport";
		var formatTotal = data.total;
		formatTotal = formatMoney(formatTotal);
		var oPrice = document.createTextNode(formatTotal);
		var transport = document.createTextNode("(含运费：￥0.00)");
		div1.appendChild(oPrice);
		div2.appendChild(transport);
		td5.appendChild(div1);
		td5.appendChild(div2);
		productTr.appendChild(td5);

		// 订单状态td
		var td6 = document.createElement("td");
		td6.setAttribute("valign","center");
		td6.setAttribute("rowspan",length);
		td6.setAttribute("width","120px");
		td6.className = "orderListItemButtonTD orderItemOrderInfoPartTD";

		var a = document.createElement("a");
		var button = document.createElement("button");
		if("waitConfirm" == data.status){
			a.setAttribute("href",getRootPath()+"confirmPay/"+data.id);
			button.className = "orderListItemConfirm";
			var confirm = document.createTextNode("确认收货");
			button.appendChild(confirm);
			a.appendChild(button);
			td6.appendChild(a);
			productTr.appendChild(td6);
		}
		else if("waitPay"==data.status) {
			a.setAttribute("href",getRootPath()+"alipay/"+data.id+"/"+data.total);
			button.className = "orderListItemConfirm";
			var pay = document.createTextNode("付款");
			button.appendChild(pay);
			a.appendChild(button);
			td6.appendChild(a);
			productTr.appendChild(td6);
		}
		else if("waitDelivery"==data.status) {
			var span = document.createElement("span");
			var delivery = document.createTextNode("待发货");
			button.setAttribute("link","admin/order/delivery/"+data.id);
			button.setAttribute("onclick","askToDelivery()"); 
			button.setAttribute("type","button");
			button.className = "btn btn-blue-grey ask2delivery";
			var askToDelivery = document.createTextNode("提醒发货");
			button.appendChild(askToDelivery);
			span.appendChild(delivery);
			span.appendChild(button);
			td6.appendChild(span);
			productTr.appendChild(td6);
		}
		else if("waitReview"==data.status) {
			a.setAttribute("href",getRootPath()+"review/"+data.id);
			button.className = "orderListItemReview";
			var comment = document.createTextNode("评价");
			button.appendChild(comment);
			a.appendChild(button);
			td6.appendChild(a);
			productTr.appendChild(td6);
		}
		else {
			var span = document.createElement("span");
			var finish = document.createTextNode("完成");
			span.appendChild(finish);
			td6.appendChild(span);
			productTr.appendChild(td6);
		}
	}
	return productTr;
} 