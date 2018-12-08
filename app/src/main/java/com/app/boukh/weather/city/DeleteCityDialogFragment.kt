package com.app.boukh.weather.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.app.boukh.weather.R

class DeleteCityDialogFragment: DialogFragment() {

    interface DeleteCityDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    companion object {

        val EXTRA_CITY_NAME = "com.app.weather.extras_EXTRA_CITY_NAME"

        fun newInstance(cityName: String) : DeleteCityDialogFragment {
            val fragment = DeleteCityDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_CITY_NAME, cityName)
            }
            return fragment
        }
    }
    var listener: DeleteCityDialogListener? = null
    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cityName = arguments!!.getString(EXTRA_CITY_NAME)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.deletecity_title, cityName))
            .setPositiveButton(getString(R.string.deletecity_positive),
                { dialog, whichButton -> listener?.onDialogPositiveClick() })

            .setNegativeButton(getString(R.string.deletecity_negative),
                { dialog, whichButton->
                    dialog.cancel()
                    listener?.onDialogNegativeClick()
                })

        return builder.create()
    }
}