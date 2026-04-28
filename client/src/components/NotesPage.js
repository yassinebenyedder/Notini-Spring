import React, { useState, useEffect, useCallback } from 'react';
import NoteCard from './NoteCard';
import Header from './Header';
import NoteModal from './NoteModal'; 
import axios from 'axios';
import { useCookies } from 'react-cookie';

function NotesPage() {
  const [notes, setNotes] = useState([]); 
  const [searchQuery, setSearchQuery] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedNote, setSelectedNote] = useState(null);
  const [cookies] = useCookies(['access_token']);

  // Refresh notes function
  const refreshNotes = useCallback(async () => {
    const token = cookies.access_token;
    const userId = window.localStorage.getItem('userID');
    if (!token || !userId) {
      console.error("No token or userId found");
      return;
    }
    try {
      const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/notes/notes/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      });
      setNotes(response.data); 
    } catch (error) {
      console.error("Error fetching notes:", error);
    }
  }, [cookies.access_token]);
  // Fetch notes from the backend
  useEffect(() => {
    refreshNotes();
  }, [refreshNotes]);

  // Delete a note
  const deletenote = async (id) => {
    const token = cookies.access_token;

    if (!token) {
      console.error("No token found");
      return;
    }

    try {
      await axios.delete(`${process.env.REACT_APP_API_BASE_URL}/notes/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`, // Send token as Bearer in headers
        },
      });
      // Refresh notes after deletion
      refreshNotes(); 
    } catch (error) {
      console.error("Error deleting note:", error);
    }
  };

// Pin a note
const pinNote = async (id) => {
  const token = cookies.access_token;
  if (!token) {
    console.error("No token found");
    return;
  }
  const noteToUpdate = notes.find(note => note.id === id); // Find the note to toggle
  if (!noteToUpdate) {
    console.error("Note not found");
    return;
  }
  try {
     // Toggle the pinned status
    const newPinnedStatus = !noteToUpdate.pinned;
    await axios.patch(`${process.env.REACT_APP_API_BASE_URL}/notes/pinned/${id}?pinned=${newPinnedStatus}`, {}, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    // Refresh notes after pinning
      refreshNotes();
  } catch (error) {
    console.error("Error pinning note:", error);
  }
};

  // Filter notes based on the search query
  const filteredNotes = notes.filter(note => {
    const query = searchQuery.trim().toLowerCase();
    return (
      note.title.toLowerCase().includes(query) ||
      note.tags.some(tag => tag.toLowerCase().includes(query))
    );
  }).sort((a, b) => {
    // Sort by pinned status (pinned notes first)
    if (a.pinned && !b.pinned) return -1;
    if (!a.pinned && b.pinned) return 1;
    // If both have same pinned status, sort by updated date (newest first)
    return new Date(b.updatedAt) - new Date(a.updatedAt);
  });

  const openModal = (note) => {
    setSelectedNote(note);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedNote(null);
  };

  return (
    <div>
      <Header userName={window.localStorage.getItem('username')} onSearch={(query) => setSearchQuery(query)} />
      <div className="notes-grid">
        {filteredNotes.map((note) => {
          // Format the date
          const date = new Date(note.updatedAt);
          const formattedDate = `${String(date.getDate()).padStart(2, '0')}/${String(date.getMonth() + 1).padStart(2, '0')}/${date.getFullYear()}`;

          return (
            <NoteCard
              key={note.id}
              title={note.title}
              date={formattedDate}
              content={note.content}
              tags={note.tags}
               // Open modal with the clicked note
              onClick={() => openModal(note)}
               // Pass the delete function
              onclickdelete={() => deletenote(note.id)} 
              onclickpin={() => pinNote(note.id)}
               pinned={note.pinned} 
            />
          );
        })}
      </div>

      <button className="add-note-button" onClick={() => openModal(null)}>+</button>

      {/* Use the NoteModal component */}
      <NoteModal
        isOpen={isModalOpen}
        closeModal={closeModal}
        // Pass the selected note to the modal
        selectedNote={selectedNote}
        onNoteSaved={refreshNotes}
      />
    </div>
  );
}
export default NotesPage;
