package ru.gmpopov.recipeapp.core.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.gmpopov.recipeapp.ui.theme.Dimens

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
            onClick = { onCategoriesClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            modifier = Modifier
                .padding(Dimens.PaddingMain)
                .weight(1f)
        ) {
            Text("Категории")
        }

        Button(
            onClick = { onFavoriteClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            modifier = Modifier
                .padding(Dimens.PaddingMain)
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