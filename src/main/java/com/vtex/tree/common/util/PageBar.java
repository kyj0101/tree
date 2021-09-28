package com.vtex.tree.common.util;

public class PageBar {
	
	public static String getPageBar(int totalContents, int cPage, int numPerPage, String url) {
		StringBuilder pageBar = new StringBuilder();
		
		int pageBarSize = 5;
		int totalPage = (int) Math.ceil((double) totalContents / numPerPage);

		url = url + (url.indexOf("?") > -1 ? "&" : "?") + "cPage=";

		int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;
		
		int pageNo = pageStart;
		pageBar.append("<nav><ul class=\"pagination justify-content-center\">\n");
		
		if(cPage == 1) {
			pageBar.append("");
		}
		else {
			pageBar.append("<li class=\"page-item\">\r\n" + 
					"      <a class=\"page-link\" href=\"javascript:paging(" + (cPage - 1) + ")\" tabindex=\"-1\">&laquo;</a>\r\n" + 
					"    </li>\n");
		}
		
		while(pageNo <= pageEnd && pageNo <= totalPage) {
			
			if(pageNo == cPage) {
				pageBar.append("<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + pageNo + "</a></li>\n");
			}
			
			else {
				pageBar.append("<li class=\"page-item\"><a class=\"page-link\" href=\"javascript:paging(" + pageNo + ")\">" + pageNo + "</a></li>\n");
			}
			
			pageNo++;
		}

		if(cPage >= totalPage) {
			pageBar.append("");
		}
		else {
			pageBar.append("<li class=\"page-item\">\r\n" + 
					"      <a class=\"page-link\" href=\"javascript:paging(" + (cPage + 1) + ")\">&raquo;</a>\r\n" + 
					"    </li>\n");
		}
		
		pageBar.append("</ul></nav>\n");
		pageBar.append("<script>function paging(pageNo){ location.href = '" + url + "' + pageNo; }</script>");
		
		return pageBar.toString();
	}
	
	public static int getOffset(int cPage, int numperPage){
		return (cPage - 1) * numperPage;
	}

	
	
}
