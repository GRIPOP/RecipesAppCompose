package ru.gmpopov.recipeapp.cori.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        ){
            Text("Категории")
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        ) {
            Text("Избранное")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigation() {
    BottomNavigation(
        onCategoriesClick = { },
        onFavoriteClick = { }
    )
}