package com.example.appnotes;

<<<<<<< HEAD
=======
import java.sql.Connection;
>>>>>>> 5c36c321e7fc8c4690d939c2304bcef1a30bcdbb
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class NoteDAO extends BaseDAO<Note> {

    @Override
    protected String getTableName() {
        return "notes";
    }

    @Override
    public void add(Note note) {
=======
public class NoteDAO {
    private Connection connection;

    public NoteDAO() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addNote(Note note) {
>>>>>>> 5c36c321e7fc8c4690d939c2304bcef1a30bcdbb
        String sql = "INSERT INTO notes (title, content, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setInt(3, note.getUserId());
            pstmt.executeUpdate();
            System.out.println("Note added: " + note.getTitle());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    @Override
    public void update(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setInt(3, note.getId());
            pstmt.executeUpdate();
            System.out.println("Note updated: " + note.getTitle());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Note findById(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("user_id")
                );
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

=======
>>>>>>> 5c36c321e7fc8c4690d939c2304bcef1a30bcdbb
    public List<Note> getNotesByUserId(int userId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("user_id")
                );
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

<<<<<<< HEAD
=======
    public void updateNote(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setInt(3, note.getId());
            pstmt.executeUpdate();
            System.out.println("Note updated: " + note.getTitle());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(int noteId) {
        String sql = "DELETE FROM notes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, noteId);
            pstmt.executeUpdate();
            System.out.println("Note deleted: " + noteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

>>>>>>> 5c36c321e7fc8c4690d939c2304bcef1a30bcdbb
    public Note getNoteByTitleAndUserId(String title, int userId) {
        String sql = "SELECT * FROM notes WHERE title = ? AND user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
