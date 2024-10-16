package  by.komarovskaya.diplomamap.lib

internal interface OsmAndNode {
    fun onAttached() {}
    fun onRemoved() {}
    fun onCleared() {}
}

internal object OsmNodeRoot : OsmAndNode