package com.zhbitzwz.vehiclesystems.MyView;

/**
 * IMenu接口
 * Author: ZWZ(native author https://github.com/linroid/FilterMenu)
 * Date: 2016/10/5 on 2:17.
 */
public interface IMenu {
    void collapse(boolean animate);
    void expand(boolean animate);
    void toggle(boolean animate);
    void setMenuLayout(FilterMenuLayout layout);
}
