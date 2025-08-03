import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }


    @Test
    fun add() {
        // Arrange
        val post = Post(
            id = 0,
            authorId = 123,
            authorName = "Edward",
            text = "Hello, its my first post"
        )

        // Act
        val addedPost = WallService.add(post)

        // Assert
        assertEquals(1, addedPost.id)
        assertEquals(1, WallService.lastId)
        assertEquals(1, WallService.posts.size)
        assertEquals("Hello, its my first post", addedPost.text)
        assertTrue(WallService.posts.contains(addedPost))
    }

    @Test
    fun update() {
        // Arrange
        val originalPost = Post(
            id = 1,
            authorName = "Edward",
            text = "Original text"
        )
        WallService.add(originalPost)

        val updatedPost = Post(
            id = 1,
            authorName = "Edward",
            text = "Updated text"
        )

        // Act
        val result = WallService.update(updatedPost)

        // Assert
        assertTrue("должно вернуть true", true)
        assertEquals("Updated text", WallService.posts[0].text)
        assertEquals(1, WallService.posts[0].id)
    }

    @Test
    fun `update non-existing post should return false`() {
        // Arrange
        val post = Post(id = 1, authorName = "Edward", text = "Original text")
        WallService.add(post)

        val nonExistingPost = Post(id = 999, authorName = "Unknown", text = "Not exists")

        // Act
        val result = WallService.update(nonExistingPost)

        // Assert
        assertFalse("должно вернуть false", false)
        assertEquals("Original text", WallService.posts[0].text)
    }
}