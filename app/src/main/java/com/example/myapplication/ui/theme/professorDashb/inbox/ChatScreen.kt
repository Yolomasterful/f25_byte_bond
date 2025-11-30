package com.example.myapplication.ui.theme.professorDashb.inbox

//import androidx.compose.foundation.layout.Column
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
////import com.example.myapplication.ui.theme.professorDashb.inbox.data.MessagingRepository
//import androidx.compose.runtime.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontVariation.width
//import androidx.compose.ui.unit.dp
////import com.example.myapplication.ui.theme.professorDashb.inbox.data.Messages
//import kotlinx.coroutines.*
//
//@Composable
//fun ChatScreen(
//    conversationId: String,
////    repository: MessagingRepository = MessagingRepository()
//) {
////    var messages by remember { mutableStateOf(listOf<Message>())}
//    var input by remember { mutableStateOf("") }
//
////    LaunchedEffect(Unit) {
////        repository.listenforMessages(conversationId) { list: List<Message> ->
////            messages = list
////        }
////    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
////        LazyColumn(
////            modifier = Modifier
////                .weight(1f)
////                .padding(2.dp)
////        ) {
////            items(messages) { msg ->
////                Text("${msg.senderId}: ${msg.text}")
////                Spacer(modifier = Modifier.height(8.dp))
////            }
//        }
//
//        Row(modifier = Modifier.padding(2.dp)) {
//            TextField(
//                value = input,
//                onValueChange = { input = it},
//                modifier = Modifier.weight(1f)
//            )
//
//            Spacer(modifier = width(1f) as Modifier)
//
//            Button(onClick = {
//                val m = Message(
//                    text = input,
//                    senderId = "Professor X",
//                    timestamp = System.currentTimeMillis()
//                )
//                input = ""
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    repository.sendMessage(conversationId, m)
//                }
//            }) {
//                Text("Send")
//            }
//        }
//    }
//}
//
//private fun MessagingRepository.listenforMessages(
//    conversationId: String,
//    function: Any
//) {
//}
