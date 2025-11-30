package com.example.myapplication.ui.theme.professorDashb.inbox.data

//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//import com.google.firebase.firestore.ktx.toObjects
//
//
//// Firstly we must define what a message is and we can do that by
//// setting it as data.
//data class Message(
//    val text: String = "",
//    val senderId: String = "",
//    val timestamp: Long = 0L
//)
//
//class MessagingRepository(
//    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//
//) {
//    fun listenForMessages(
//        conversationId: String,
//        onMessages: (List<Message>) -> Unit) {
//        firestore.collection("messages")
//            .document(conversationId)
//            .collection("chat")
//            .orderBy("timestamp")
//            .addSnapshotListener { snapshot, _ ->
//                if (snapshot != null) {
//                    val list = snapshot.toObjects(Message::class.java)
//                    onMessages(list)
//                }
//            }
//    }
//
//    suspend fun sendMessage(conversationId: String, message: Message) {
//        firestore.collection("messages")
//            .document(conversationId)
//            .collection("chat")
//            .add(message)
//            .await()
//    }
//}