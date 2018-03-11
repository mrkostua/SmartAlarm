package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.mrkostua.mathalarm.Interfaces.AddInjection
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.PreferencesConstants
import com.mrkostua.mathalarm.Tools.ShowLogs
import com.mrkostua.mathalarm.extensions.app
import com.mrkostua.mathalarm.extensions.get
import com.mrkostua.mathalarm.extensions.set
import kotlinx.android.synthetic.main.fragment_option_set_message.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 08.12.2017.
 */
class FragmentOptionSetMessage : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface,AddInjection {
    //can be only set after onAttach() otherwise getContext() return null
    override lateinit var fragmentContext: Context

    private val TAG = FragmentOptionSetTime::class.java.simpleName
    @Inject
    public lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity.applicationContext
        initializeDependOnContextVariables(fragmentContext)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ShowLogs.log(TAG, "onCreateView")
        return inflater?.inflate(R.layout.fragment_option_set_message, container, false)
    }

    override fun onResume() {
        super.onResume()
        initializeTextViewWithLastMessage()
        saveSettingsInSharedPreferences()
    }

    override fun onAttach(context: Context?) {
        injectDependencies()
        super.onAttach(context)
    }
    override fun injectDependencies() {
        app.applicationComponent.inject(this)

    }

    override fun initializeDependOnContextVariables(context: Context) {
    }


    override fun initializeDependOnViewVariables(view: View?) {
    }

    override fun saveSettingsInSharedPreferences() {
        etSetAlarmMessage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ShowLogs.log(TAG, "message is : " + s?.toString())
                Toast.makeText(fragmentContext, getString(R.string.toast_message_was_saved), Toast.LENGTH_LONG).show()
                sharedPreferences[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue()] = s?.toString()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    private fun initializeTextViewWithLastMessage() {
        val savedMessage = sharedPreferences[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue(), ""]
                ?: ""
        etSetAlarmMessage.setText(savedMessage
                , TextView.BufferType.EDITABLE)
    }
}