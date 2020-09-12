package com.vezdekod.donate.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.esafirm.imagepicker.features.ImagePicker
import kotlinx.android.synthetic.main.fragment_payment_creation.*
import kotlinx.android.synthetic.main.layout_dropdown.view.*
import kotlinx.android.synthetic.main.layout_text_input.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import com.vezdekod.donate.R
import com.vezdekod.donate.model.PaymentType

class PaymentCreationFragment : Fragment() {

    private val args: PaymentCreationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_payment_creation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageLoadBtn.setOnClickListener {
        }
        initViews()
    }

    private fun initViews() {
        toolbar.title.text = when (args.paymentType) {
            PaymentType.REGULAR -> "Регулярный сбор"
            PaymentType.TARGET -> "Целевой сбор"
        }
        toolbar.backBtn.isVisible = true
        toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        paymentNameInput.inputTitleTv.text = "Название сбора"
        paymentNameInput.inputFieldEt.hint = "Название сбора"

        paymentAmountInput.inputTitleTv.text = when (args.paymentType) {
            PaymentType.REGULAR -> "Сумма в месяц, ₽"
            PaymentType.TARGET -> "Сумма, ₽"
        }

        paymentAmountInput.inputFieldEt.hint = when (args.paymentType) {
            PaymentType.REGULAR -> "Сколько нужно в месяц?"
            PaymentType.TARGET -> "Сколько нужно собрать?"
        }

        paymentTarget.inputTitleTv.text = "Цель"
        paymentTarget.inputFieldEt.hint = when (args.paymentType) {
            PaymentType.REGULAR -> "Например, поддержка приюта"
            PaymentType.TARGET -> "Например, лечение человека"
        }

        paymentDescription.inputTitleTv.text = "Описание"
        paymentDescription.inputFieldEt.hint = "На что пойдут деньги и как они кому-то помогут"

        whereToGetMoneyContainer.dropdownTitle.text = "Куда получать деньги"
        whereToGetMoneyContainer.dropdownSelector.text = "Счёт VK Pay • 1234"

        authorContainer.isVisible = args.paymentType == PaymentType.REGULAR
        authorContainer.dropdownTitle.text = "Автор"
        authorContainer.dropdownSelector.text = "Elon Musk"

        confirmBtn.text = when (args.paymentType) {
            PaymentType.REGULAR -> "Далее"
            PaymentType.TARGET -> "Создать сбор"
        }

        imageLoadBtn.setOnClickListener {
            ImagePicker.create(this)
                .start()
        }
        imageDeleteBtn.setOnClickListener {
            imageLoadBtn.setImageResource(R.drawable.bacgkround_empty)
            imageLoadBtn.setOnClickListener {
                ImagePicker.create(this)
                    .start()
            }
            imageLoadHint.isVisible = true
            it.isVisible = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            ImagePicker.getImages(data).firstOrNull()?.let {
                imageLoadBtn.load(it.uri) {
                    crossfade(true)
                }
                imageLoadBtn.setOnClickListener(null)
                imageDeleteBtn.isVisible = true
                imageLoadHint.isVisible = false
            }

        }
    }
}