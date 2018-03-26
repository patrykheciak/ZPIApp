package com.zpi.zpiapp.model

data class Drug(
        var idDrug : Int,
        var name: String,
        var volume: Int,
        var dosageForm: String,
        var ingredientsList: List<DrugIngredient>
)