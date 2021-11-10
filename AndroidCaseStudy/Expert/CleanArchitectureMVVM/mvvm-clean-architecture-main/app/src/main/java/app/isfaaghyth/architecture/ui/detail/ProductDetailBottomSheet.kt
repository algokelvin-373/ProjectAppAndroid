package app.isfaaghyth.architecture.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import app.isfaaghyth.architecture.databinding.DialogProductDetailBinding
import app.isfaaghyth.architecture.ui.uimodel.ProductUIModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductDetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DialogProductDetailBinding? = null
    private val binding: DialogProductDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogProductDetailBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        arguments?.let {
            val data = it.getParcelable<ProductUIModel>(KEY_PRODUCT_DETAIL)?: return

            binding.txtName.text = data.name
            binding.txtPrice.text = priceIdr(data.price)
        }
    }

    private fun priceIdr(price: Int): String {
        return "Rp.$price,-"
    }

    companion object {
        private val TAG = ProductDetailBottomSheet::class.java.name

        private const val KEY_PRODUCT_DETAIL = "product_detail"

        fun show(fm: FragmentManager, product: ProductUIModel) {
            val sheet = ProductDetailBottomSheet()
            sheet.arguments = Bundle().apply {
                putParcelable(KEY_PRODUCT_DETAIL, product)
            }
            sheet.show(fm, TAG)
        }
    }

}