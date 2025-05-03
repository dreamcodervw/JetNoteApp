package com.dreamcoder.jetnoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dreamcoder.jetnoteapp.model.Note
import com.dreamcoder.jetnoteapp.screen.NoteScreen
import com.dreamcoder.jetnoteapp.screen.NoteViewModel
import com.dreamcoder.jetnoteapp.ui.theme.JetNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetNoteAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    val viewModel: NoteViewModel by viewModels()
                    NotesApp(viewModel)
                }
            }
        }
    }

    @Composable
    fun NotesApp(noteViewModel: NoteViewModel = viewModel()) {
        val noteList = noteViewModel.noteList.collectAsState().value
        NoteScreen(notes = noteList, onAddNote = {
            noteViewModel.addNote(it)
        }, onRemoveNote = {
            noteViewModel.removeNote(it)
        })
    }
}