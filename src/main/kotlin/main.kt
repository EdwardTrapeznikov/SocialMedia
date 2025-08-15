import java.time.LocalDateTime
import kotlin.math.truncate

fun main() {
    try {
        WallService.add(
            Post(
                0, 0, "Edward", "Hello, its my first post", 0,
                0, 0, false, false, true, false,
                Comments(), Likes(), null, null, emptyList()
            )
        )
        WallService.add(
            Post(
                0, 0, "Edward", "Hello, its my second post", 0,
                0, 0, false, false, true, false,
                Comments(), Likes(), null, null, emptyList()
            )
        )
        WallService.printPost()

        // Пример добавления комментария
        val post = WallService.add(
            Post(
                0, 0, "Alice", "Test post", 0,
                0, 0, false, false, true, false,
                Comments(), Likes(), null, null, emptyList()
            )
        )

        val comment = Comment(0, 123, "Отличный пост!", Donut())
        val addedComment = WallService.createComment(post.id, comment)
        println("Комментарий добавлен: ${addedComment.text}")

    } catch (e: PostNotFoundException) {
        println("Ошибка: ${e.message}")
    }
}

data class Post(
    val id: Int = 0,
    val authorId: Int = 0,
    val authorName: String = "",
    val text: String? = null,
    val viewsCount: Int = 0,
    val likesCount: Int = 0,
    val sharesCount: Int = 0,
    val isLiked: Boolean = false,
    val isShared: Boolean = false,
    val isPublished: Boolean = true,
    val isRepost: Boolean = false,
    val comments: Comments = Comments(),
    val likes: Likes = Likes(),
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val attachments: List<Attachment> = emptyList()
)

data class Comments(
    val count: Int = 0,
    val canComment: Boolean = true,
    val canSeeAll: Boolean = true,
    val hasMore: Boolean = false
)

data class Likes(
    val count: Int = 0,
    val canLike: Boolean = true,
    val canPublish: Boolean = true,
    val userLikes: Boolean = false,
    val myLike: Boolean = false
)

data class Comment(
    val id: Int = 0,
    val fromId: Int = 0,
    val text: String = "",
    val donut: Donut = Donut(),
    val replyToUser: Int? = null,
    val replyToComment: Int? = null,
    val attachments: List<Attachment> = emptyList(),
    val parentsStack: List<Int> = emptyList(),
    val thread: ThreadInfo = ThreadInfo()
)

data class Donut(
    val isDon: Boolean = false,
    val placeholder: String? = null
)

data class ThreadInfo(
    val count: Int = 0,
    val items: List<Comment> = emptyList(),
    val canPost: Boolean = true,
    val showReplyButton: Boolean = true,
    val groupsCanPost: Boolean = false
)

interface Attachment {
    val type: String
}

data class PhotoAttachment(
    override val type: String = "photo",
    val photo: Photo
) : Attachment

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo130: String?,
    val photo604: String?
)

data class VideoAttachment(
    override val type: String = "video",
    val video: Video
) : Attachment

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int // секунды
)

data class AudioAttachment(
    override val type: String = "audio",
    val audio: Audio
) : Attachment

data class Audio(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val artist: String,
    val duration: Int // секунды
)

data class DocumentAttachment(
    override val type: String = "doc",
    val document: Document
) : Attachment

data class Document(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val url: String,
    val size: Int // байты
)

data class LinkAttachment(
    override val type: String = "link",
    val link: Link
) : Attachment

data class Link(
    val url: String,
    val title: String,
    val description: String?
)

class PostNotFoundException(message: String) : Exception(message)

object WallService {
    var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    var lastId = 0
    private var lastCommentId = 0

    fun add(post: Post): Post {
        val newPost = if (post.id == 0) {
            val id = ++lastId
            post.copy(id = id)
        } else {
            post
        }
        posts += newPost
        return newPost
    }

    fun update(new: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (new.id == post.id) {
                posts[index] = new.copy()
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        lastId = 0
        lastCommentId = 0
    }

    fun printPost() {
        for (post in posts) {
            println(post)
            println()
        }
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        // Проверяем, существует ли пост
        val postExists = posts.any { it.id == postId }
        if (!postExists) {
            throw PostNotFoundException("Пост с ID $postId не найден")
        }

        // Присваиваем ID комментарию
        val newComment = if (comment.id == 0) {
            val id = ++lastCommentId
            comment.copy(id = id)
        } else {
            comment
        }

        // Добавляем в массив
        comments += newComment
        return newComment
    }
}


