package cn.orange.core;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public interface BaseView<P> {

    /**
     * 关联View与Presenter
     *
     * @param presenter 业务处理
     */
    void setPresenter(P presenter);

    /**
     * Whether or NOT the View has attached,
     *
     * @return for Activity return !isFinish(),
     * for Fragment return isAdded(),
     * for Dialog return isShowing()
     */
    boolean isAdded();
}
