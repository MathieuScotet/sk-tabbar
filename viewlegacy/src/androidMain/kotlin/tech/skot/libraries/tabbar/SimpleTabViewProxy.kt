package tech.skot.libraries.tabbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.skot.core.components.SKActivity
import tech.skot.core.components.SKComponentViewProxy
import tech.skot.core.view.Color
import tech.skot.core.view.Icon
import tech.skot.libraries.tabbar.viewlegacy.R
import tech.skot.libraries.tabbar.viewlegacy.databinding.SimpleTabBinding
import tech.skot.view.live.MutableSKLiveData
import tech.skot.view.live.SKMessage

class SimpleTabViewProxy(
        override val label: String,
        override val onTap: Function0<Unit>,
        iconInitial: Icon,
        labelColorInitial: Color,
        override val translateY: Boolean,
        centerTextInitial:String?
) : SKComponentViewProxy<SimpleTabBinding>(), SimpleTabVC {
    private val iconLD: MutableSKLiveData<Icon> = MutableSKLiveData(iconInitial)

    override var icon: Icon by iconLD

    private val labelColorLD: MutableSKLiveData<Color> = MutableSKLiveData(labelColorInitial)

    override var labelColor: Color by labelColorLD

    override val layoutId: Int = R.layout.simple_tab

    private val centerTextLD: MutableSKLiveData<String?> = MutableSKLiveData(centerTextInitial)

    override var centerText: String? by centerTextLD

    data class AnimateIconMessageData(val newVal:String, val onEnd: () -> Unit)
    private val animateIconMessage = SKMessage<AnimateIconMessageData>()

    override fun animateIcon(newVal: String, onEnd: () -> Unit) {
        animateIconMessage.post(message = AnimateIconMessageData(newVal = newVal, onEnd = onEnd))
    }
    override fun saveState() {
    }

    override fun bindingOf(view: View): SimpleTabBinding = SimpleTabBinding.bind(view)

    override fun inflate(
            layoutInflater: LayoutInflater,
            parent: ViewGroup?,
            attachToParent: Boolean
    ): SimpleTabBinding = SimpleTabBinding.inflate(layoutInflater, parent, attachToParent)
    override fun bindTo(
            activity: SKActivity,
            fragment: Fragment?,
            binding: SimpleTabBinding,
            collectingObservers: Boolean
    ): SimpleTabView = SimpleTabView(this, activity, fragment, binding).apply {
        collectObservers = collectingObservers
        onLabel(label)
        onOnTap(onTap)
        onTranslateY(translateY)
        iconLD.observe {
            onIcon(it)
        }
        labelColorLD.observe {
            onLabelColor(it)
        }
        centerTextLD.observe {
            onCenterText(it)
        }
        animateIconMessage.observe {
            animateIcon(it.newVal, it.onEnd)
        }
    }
}

interface SimpleTabRAI {
    fun onLabel(label: String)

    fun onOnTap(onTap: Function0<Unit>)

    fun onIcon(icon: Icon)

    fun onLabelColor(labelColor: Color)

    fun onTranslateY(value:Boolean)

    fun onCenterText(centerText:String?)

    fun animateIcon(newVal: String, onEnd: () -> Unit)
}
