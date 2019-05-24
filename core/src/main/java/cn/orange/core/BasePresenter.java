package cn.orange.core;

/**
 * Created by ChengHe.Zhang on 2019/4/1.
 * Email:chengheZhang@kaifa.cn
 */
public interface BasePresenter {

    /**
     * 当View对象被销毁时,调用此方法,
     * 以便在Presenter中释放资源
     */
    void destroy();
}
