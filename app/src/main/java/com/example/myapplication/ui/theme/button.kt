package com.example.myapplication.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import android.R.attr.text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldDefaults

@Composable
fun backArrow(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,

) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
    ) {
        Button(
            onClick = {
                onBackClick?.invoke()
                    ?: navController.popBackStack()
//                navController.popBackStack()
                      },
            modifier = Modifier
                .width(93.dp)
                .height(28.dp)
                .align(Alignment.TopStart),
            shape = RoundedCornerShape(size = 31.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, Color.Black)


        ) {
            Image(
                painter = painterResource(id = R.mipmap.backarrow),
                contentDescription = "ArrowBar",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

// Used to represent the messaging boxes
@Composable
fun inboxBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit

) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(1.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.padding(horizontal = 11.dp)

        ) {
        content()
    }
}

@Composable
fun basicButton(
    modifier: Modifier = Modifier,
    imageContent: @Composable () -> Unit,
    onClick:() -> Unit,

    ) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(190.dp)
            .height(65.dp),
        shape = RoundedCornerShape(size = 31.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        imageContent()
    }

}



@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun SearchDropdown(
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    var text by remember { mutableStateOf("")}

    val filtered = options.filter {
        it.contains(text, ignoreCase = true)
    }

    ExposedDropdownMenuBox(
        expanded = expanded && filtered.isNotEmpty(),
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = text,
            onValueChange = {newText ->
                text = newText
                expanded = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
            ),

            placeholder = { Text("") },
            singleLine = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded && filtered.isNotEmpty()
                )
            }


        )

        ExposedDropdownMenu(
            expanded = expanded && filtered.isNotEmpty(),
            onDismissRequest = { expanded = false}

        ) {
            filtered.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option)},
                    onClick = {
                        text = option
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }


    }
}

@Composable
fun ExposedDropdownMenu(expanded: Boolean, onExpandedChange: () -> Unit) {
    TODO("Not yet implemented")
}