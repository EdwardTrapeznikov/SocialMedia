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

    @Test
    fun `createComment should add comment to existing post`() {
        // Arrange: Создаем пост
        val post = WallService.add(Post(
            id = 0,
            authorName = "Alice",
            text = "Test post"
        ))

        val comment = Comment(
            id = 0,
            fromId = 123,
            text = "This is a test comment"
        )

        // Act: Добавляем комментарий
        val addedComment = WallService.createComment(post.id, comment)

        // Assert: Проверяем, что комментарий добавлен правильно
        assertNotNull(addedComment)
        assertEquals(123, addedComment.fromId)
        assertEquals("This is a test comment", addedComment.text)
        assertTrue(addedComment.id > 0) // ID должен быть присвоен
    }

    @Test(expected = PostNotFoundException::class)
    fun `createComment should throw exception for non-existing post`() {
        // Arrange: Создаем комментарий
        val comment = Comment(
            id = 0,
            fromId = 456,
            text = "Orphan comment"
        )

        // Act & Assert: Пытаемся добавить комментарий к несуществующему посту (ID = 999)
        // Ожидаем, что будет брошено PostNotFoundException
        WallService.createComment(999, comment)
    }

}