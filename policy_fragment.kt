package com.hotellina.SouvenirScout


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.navigation.fragment.findNavController
import com.hotellina.SouvenirScout.databinding.FragmentPolicyFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class policy_fragment : Fragment() {
    private lateinit var binding: FragmentPolicyFragmentBinding
    val TAG : String = "*policy_fragment"
    val policy_agreed = booleanPreferencesKey("policy_agreed")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view = ComposeView(requireContext())
        view.apply{
            setContent {
                layout()
            }
        }
        return view
    }

    @Preview(showBackground = true)
    @Composable
    fun layout() {
        Column( horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ){
            IconsTop()
            policytext()
            buttons()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun IconsTop() {
        Row(verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier.padding(top = 20.dp)
        ){
            Image(
                painter = painterResource(R.drawable.baseline_info_24),
                contentDescription = "Contact profile picture",
            )
            Spacer(modifier = Modifier.width(70.dp))
            Image(
                painter = painterResource(R.drawable.vendorsmenuitem),
                contentDescription = "Contact profile picture",
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun policytext(){
        Text(text = stringResource(id = R.string.prominent_disclosure),
            Modifier.padding(20.dp))
    }

    @Preview(showBackground = true)
    @Composable
    fun buttons(){
        Row(modifier = Modifier.padding(bottom = 20.dp)
        ){
            Button(onClick = { deny()},
                    ) {
                Text("No")
                Image(painter = painterResource(id =  R.drawable.baseline_clear_24),
                                                contentDescription = "I do not agree and do NOT login")
            }
            Spacer(modifier = Modifier.width(70.dp))
            Button(onClick = { agree() },
            ) {
                Text("Agree")
                Image(painter = painterResource(id =  R.drawable.baseline_done_24),
                                                contentDescription = "Agree and login")
            }
        }
     }

    private fun agree() {
        Log.d(TAG, "accepted")
        agree_policy_preferences(true)
        val nav_controller = findNavController()
        nav_controller.navigate(R.id.action_policy_fragment_to_loginRegistration_FR_accept,null)
    }

    private fun deny() {
        Log.d(TAG, "denied")
        agree_policy_preferences(false)
        val nav_controller = findNavController()
        nav_controller.navigate(R.id.action_policy_fragment_to_loginRegistration_FR_denied,null)
    }

    fun agree_policy_preferences(agreed:Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            save_preference(agreed)
        }
    }

    suspend fun save_preference(agreed:Boolean) {
        requireContext().dataStore.edit { settings ->
            settings[policy_agreed] = agreed
        }
    }
}
