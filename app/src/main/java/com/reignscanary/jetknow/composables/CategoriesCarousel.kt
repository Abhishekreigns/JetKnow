package com.reignscanary.jetknow.composables


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reignscanary.jetknow.backend.Category
import com.reignscanary.jetknow.backend.MainScreenViewModel

@Composable
fun CategoriesCarousel(modifier: Modifier)

{
    val mainScreenViewModel : MainScreenViewModel = viewModel()
    val selectedCategory = mainScreenViewModel.selectedCategory.observeAsState(initial = false)
    val categoryList : List<Category> = listOf(
        Category("Rents"),
        Category("Electrician"),
        Category("Plumbers"),
        Category("Developer"),
        Category("Laundry"),
        Category("Barber"),
        Category("Dairy")
    )

    LazyRow(
        Modifier
            .padding(start = 10.dp, end = 10.dp)
            .clip(MaterialTheme.shapes.large)

    ) {

        item {
            for (i in categoryList) {
                CategoryCard(
                    modifier
                        .clip(MaterialTheme.shapes.large),
                    category =
                    i.category,
                    isSelected = (selectedCategory.value == i.category),
                    onSelectedCategoryChanged = {
                        mainScreenViewModel.onSelectedCategoryChanged(it)
                    }

                )
            }
        }

    }



}



