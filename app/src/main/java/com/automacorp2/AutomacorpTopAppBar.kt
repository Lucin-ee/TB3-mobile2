package com.automacorp2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutomacorpTopAppBar(
    title: String,
    onNavigationClick: () -> Unit
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            // Action 1: Navigate to RoomListActivity
            IconButton(onClick = {
                val intent = Intent(context, RoomListActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(painter = painterResource(R.drawable.ic_action_rooms), contentDescription = "Open Rooms")
            }

            // Action 2: Send an email
            IconButton(onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:your_email@example.com") // Replace with a valid email address
                    putExtra(Intent.EXTRA_SUBJECT, "Automacorp Feedback")
                }
                context.startActivity(emailIntent)
            }) {
                Icon(painter = painterResource(R.drawable.ic_action_mail), contentDescription = "Send email")
            }


            // Action 3: Open GitHub page
            IconButton(onClick = {
                val githubUrl = "https://github.com/Lucin-ee"
                val githubIntent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
                context.startActivity(githubIntent)
            }) {
                Icon(painter = painterResource(R.drawable.ic_action_github), contentDescription = "Open GitHub")
            }

        }
    )
}

@Preview
@Composable
fun PreviewAutomacorpTopAppBar() {
    AutomacorpTopAppBar(
        title = "Room Details",
        onNavigationClick = { /* Handle navigation */ }
    )
}
