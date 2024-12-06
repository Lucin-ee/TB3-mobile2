package com.automacorp2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.automacorp2.ui.theme.Automacorp2Theme


@Composable
fun Greeting(onClick: (name: String) -> Unit, modifier: Modifier = Modifier) {
    Column {
        AppLogo(Modifier.padding(top = 32.dp).fillMaxWidth())
        Text(
            stringResource(R.string.act_main_welcome),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )

        var name by remember { mutableStateOf("") }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            placeholder = {
                Text(stringResource(R.string.act_main_fill_name))
            }
        )

        Button(
            onClick = { onClick(name) },
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.act_main_open))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Automacorp2Theme {
        Greeting(
            onClick = {},
            modifier = Modifier
        )
    }
}
