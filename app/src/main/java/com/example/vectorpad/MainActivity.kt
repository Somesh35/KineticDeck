package com.example.vectorpad

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.net.Socket

/**
 * [MainActivity] is the main entry point for the VectorPad application.
 * It displays a simple user interface with a button that, when clicked,
 * sends a greeting message to a server running on the host PC via a TCP socket.
 */
class MainActivity : ComponentActivity(){

    /**
     * Initializes the activity, setting up the Jetpack Compose UI.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier= Modifier.fillMaxSize()) {
                Box(contentAlignment = Alignment.Center) {
                    Button(onClick = {
                        // Launch a coroutine in the IO dispatcher for network operations
                        // to avoid blocking the main UI thread.
                        CoroutineScope(Dispatchers.IO).launch{
                            try{
                                // 10.0.2.2 is a special IP address that allows the Android emulator
                                // to access the loopback interface of the host machine (127.0.0.1).
                                val pcIpAddress = "10.0.2.2"
                                val port = 8080

                                // Establish a socket connection to the PC.
                                val socket = Socket(pcIpAddress, port)
                                val writer = PrintWriter(socket.getOutputStream(), true)

                                // Send the signal message.
                                writer.println("Hello from my Android phone")

                                // Close the socket to free resources.
                                socket.close()

                            }catch (e: Exception){
                                // Log any exceptions to the console for debugging.
                                e.printStackTrace()
                            }
                        }
                    }){
                        Text(text = "Send Signal to PC")
                    }
                }
            }
        }
    }
}
/**
 * import android.os.Bundle
 * import androidx.activity.ComponentActivity
 * import androidx.activity.compose.setContent
 * import androidx.compose.foundation.layout.*
 * import androidx.compose.material3.*
 * import androidx.compose.ui.Alignment
 * import androidx.compose.ui.Modifier
 * import kotlinx.coroutines.CoroutineScope
 * import kotlinx.coroutines.Dispatchers
 * import kotlinx.coroutines.launch
 * import java.io.PrintWriter
 * import java.net.Socket
 *
 * class MainActivity : ComponentActivity(){
 *     override fun onCreate(savedInstanceState: Bundle?){
 *         super.onCreate(savedInstanceState)
 *         setContent {
 *             Surface(modifier= Modifier.fillMaxSize()) {
 *                 Box(contentAlignment = Alignment.Center) {
 *                     Button(onClick = {
 *                         CoroutineScope(Dispatchers.IO).launch{
 *                             try{
 *                                 val pcIpAddress = "10.0.2.2"
 *                                 val port = 8080
 *
 *                                 var socket = Socket(pcIpAddress, port)
 *                                 val writer = PrintWriter(socket.getOutputStream(),true)
 *                                 writer.println("Hello from my Android phone")
 *
 *                                 socket.close()
 *
 *                             }catch (e: Exception){
 *                                 e.printStackTrace()
 *                             }
 *                         }
 *                     }){
 *                         Text(text = "Send Signal to PC")
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 */