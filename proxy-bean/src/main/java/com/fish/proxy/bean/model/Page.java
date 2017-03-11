package com.fish.proxy.bean.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分页信息
 * 
 * @author ZhangJunhua
 * @version v 0.1 2015年12月18日 下午4:15:30
 */
public final class Page<T> extends PageRequest implements Iterable<T>{
    protected List<T> list = null;
    protected long totalCount = 0; //总记录数
    protected int totalPage = 0; //总页数
    private int sliderCount = 9;

    public Page() {
    }

    public Page(PageRequest request) {
        this.pageIndex = request.pageIndex;
        this.pageSize = request.pageSize;
        this.countTotal = request.countTotal;
        this.orderBy = request.orderBy;
        this.orderDir = request.orderDir;
    }

    public Page(int pageIndex, int pageSize, int totalCount) {
        this.setPageIndex(pageIndex);
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
    }

    /**
     * 获得页内的记录列表.
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setList(final List<T> list) {
        this.list = list;
    }

    /**
     * 获得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
        this.totalPage = (int) Math.ceil((double) totalCount / (double) getPageSize());
    }

    /**
     * 实现Iterable接口, 可以for(Object item : page)遍历使用
     */
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 是否还有下一页.
     */
    public boolean hasNextPage() {
        return ((getPageIndex() + 1) <= getTotalPage());
    }

    /**
     * 是否最后一页.
     */
    public boolean isLastPage() {
        return !hasNextPage();
    }

    /**
     * 取得下页的页号, 序号从1开始.
     * 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (hasNextPage()) {
            return getPageIndex() + 1;
        } else {
            return getPageIndex();
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean hasPrePage() {
        return (getPageIndex() > 1);
    }

    /**
     * 是否第一页.
     */
    public boolean isFirstPage() {
        return !hasPrePage();
    }

    /**
     * 取得上页的页号, 序号从1开始.
     * 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (hasPrePage()) {
            return getPageIndex() - 1;
        } else {
            return getPageIndex();
        }
    }

    /**
     * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
     *
     * @param count 需要计算的列表大小
     * @return pageNo列表
     */
    public List<Integer> getSlider(int count) {
        int halfSize = count / 2;

        int startPageNo = Math.max(getPageIndex() - halfSize, 1);
        int endPageNo = Math.min((startPageNo + count) - 1, totalPage);

        if ((endPageNo - startPageNo) < count) {
            startPageNo = Math.max(endPageNo - count, 1);
        }

        List<Integer> newResult = new ArrayList<Integer>();
        for (int i = startPageNo; i <= endPageNo; i++) {
            newResult.add(i);
        }
        return newResult;
    }

    public List<Integer> getDefaultSlider() {
        return getSlider(sliderCount);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(super.toString() + "[");
        buf.append("pageIndex=" + getPageIndex() + ",");
        buf.append("pageSize=" + getPageSize() + ",");
        buf.append("totalCount=" + getTotalCount() + ",");
        buf.append("totalPage=" + getTotalPage() + ",");
        buf.append("sortStr=" + getSortStr() + ",");
        buf.append("result=[");

        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    buf.append(",");
                }
                buf.append("\n");
                buf.append(i + 1);
                buf.append(". ");
                buf.append(list.get(i));
            }
            buf.append("\n");
        } else {
            buf.append("]");
        }
        buf.append("]");
        return buf.toString();
    }
}