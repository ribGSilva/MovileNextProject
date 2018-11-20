package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun <T : ViewDataBinding> contentView(
    @LayoutRes layoutRes: Int,
    inflater: LayoutInflater,
    container: ViewGroup?): SetContentView<T> =
    SetContentView(layoutRes, inflater, container)

class SetContentView<out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    private val inflater: LayoutInflater,
    private val container: ViewGroup?
    ){
    private var value : T? = null

    operator fun getValue(thsFragment: Fragment, property: KProperty<*>) : T {
        value = value ?: DataBindingUtil.inflate(
            inflater, layoutRes, container, false)

        return value!!
    }
}

fun <R : BaseObservable, T : Any> bindable(
    value: T, bindingRes: Int): BindableDelegate<R, T> {
    return BindableDelegate(value, bindingRes)
}
class BindableDelegate<in R : BaseObservable, T : Any>(
    private var value: T, private val bindingEntry: Int) {
    operator fun getValue(thisRef: R, property: KProperty<*>):
            T = value

    operator fun setValue(
        thisRef: R, property: KProperty<*>,
        value: T
    ) {
        if (this.value != value) {
            this.value = value
            thisRef.notifyPropertyChanged(bindingEntry)
        }
    }
}

fun textChangeObservable(@IdRes viewId: Int):
        TextChangeObservableDelegate {
    return TextChangeObservableDelegate(viewId)
}
class TextChangeObservableDelegate(@IdRes private val viewId:
                                   Int) : ReadOnlyProperty<Fragment, Flowable<CharSequence>> {
    override fun getValue(thisRef: Fragment, property:
    KProperty<*>
    ): Flowable<CharSequence> {
        return RxTextView
            .textChanges(thisRef.view!!.findViewById(viewId))
            .skip(1)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.LATEST)
    }
}