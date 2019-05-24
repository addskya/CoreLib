package cn.orange.core;

/**
 * Created by ChengHe.Zhang on 2019/4/1.
 * Email:chengheZhang@kaifa.cn
 */
public abstract class DefaultObserver<T> extends io.reactivex.observers.DefaultObserver<T> {

    private BaseView<?> mView;

    protected DefaultObserver(BaseView<?> view) {
        mView = view;
    }

    @Override
    protected final void onStart() {
        if (isViewValid()) {
            onDoStart();
        }
    }

    protected void onDoStart() {

    }

    @Override
    public final void onNext(T t) {
        if (isViewValid())
            onDoNext(t);
    }

    protected void onDoNext(T t) {

    }

    @Override
    public final void onError(Throwable e) {
        if (isViewValid()) {
            onDoError(e);
        }
    }

    protected void onDoError(Throwable error) {

    }

    @Override
    public final void onComplete() {
        if (isViewValid()) {
            onDoComplete();
        }
    }

    protected void onDoComplete() {

    }

    private boolean isViewValid() {
        return (mView != null && mView.isAdded());
    }
}
