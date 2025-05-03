package com.dreamcoder.jetnoteapp.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreamcoder.jetnoteapp.data.NoteDataSource
import com.dreamcoder.jetnoteapp.model.Note
import com.dreamcoder.jetnoteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collectLatest {
                _noteList.value = it
            }
        }
    }


    fun addNote(note: Note) {
        viewModelScope.launch { repository.addNote(note) }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch { repository.updateNote(note) }
    }

    fun removeNote(note: Note) {
        viewModelScope.launch { repository.deleteNote(note) }
    }
}