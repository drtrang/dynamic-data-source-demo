package com.github.trang.dynamic.util;

import com.github.pagehelper.PageInfo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;

/**
 * PageInfoHelper
 *
 * @author trang
 */
@UtilityClass
public class PageInfoHelper {

    public static <T, R> PageInfo<R> mapPage(PageInfo<T> page, Function<List<T>, List<R>> function) {
        PageInfo<R> resultPage = new PageInfo<>();
        resultPage.setList(function.apply(page.getList()));
        resultPage.setSize(page.getSize());
        resultPage.setPageNum(page.getPageNum());
        resultPage.setPageSize(page.getPageSize());
        resultPage.setStartRow(page.getStartRow());
        resultPage.setEndRow(page.getEndRow());
        resultPage.setTotal(page.getTotal());
        resultPage.setHasPreviousPage(page.isHasPreviousPage());
        resultPage.setHasNextPage(page.isHasNextPage());
        resultPage.setIsFirstPage(page.isIsFirstPage());
        resultPage.setIsLastPage(page.isIsLastPage());
        resultPage.setPages(page.getPages());
        resultPage.setPrePage(page.getPrePage());
        resultPage.setNextPage(page.getNextPage());
        resultPage.setNavigatePages(page.getNavigatePages());
        resultPage.setNavigatepageNums(page.getNavigatepageNums());
        resultPage.setNavigateFirstPage(page.getNavigateFirstPage());
        resultPage.setNavigateLastPage(page.getNavigateLastPage());
        return resultPage;
    }

}