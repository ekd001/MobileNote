package tg.ulcrsandroid.mobilenote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tg.ulcrsandroid.mobilenote.data.model.Categorie
import tg.ulcrsandroid.mobilenote.data.repository.CategorieRepository

class CategorieViewModel(private val categorieRepository: CategorieRepository): ViewModel() {

    private val _categories = MutableLiveData<List<Categorie>>()
    val categories: LiveData<List<Categorie>> get() = _categories

    fun loadCategories(){
        _categories.value = categorieRepository.getAllCategorie()
    }

    fun addCategorie(categorie: Categorie){
        categorieRepository.addCategorie(categorie)
        loadCategories()
    }

    fun updateCategorie(id:String, libelle:String, description:String){
        categorieRepository.updateCategorie(id, libelle, description)
        loadCategories()
    }

    fun deleteCategorie(categorie: Categorie){
        categorieRepository.deleteCategorie(categorie.id)
        loadCategories()
    }

    override fun onCleared() {
        super.onCleared()
        categorieRepository.close()
    }
}