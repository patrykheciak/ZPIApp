package com.zpi.zpiapp.model

data class Interaction(
        var idInteraction: Int,
        var idCompound: Int,
        var comIdCompound: Int,
        var interactionInfo: String,
        var comIdCompoundNavigation: Compound,
        var idCompoundNavigation: Compound
)