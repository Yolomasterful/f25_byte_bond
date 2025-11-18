# Data Layer (Repository Layer)

This directory contains the **data access layer** of the Deferral Management System.

It abstracts all communication with:
- Firebase Firestore
- Firebase Authentication
- Firebase Storage
- Firebase Cloud Functions
- Room Booking external API (if used)

The UI and ViewModels should NEVER directly call Firebase.  
All interaction must be routed through repositories defined here.

---

## Folder Structure
data/
│
├── models/
│ ├── User.kt
│ ├── DeferralRequest.kt
│ ├── Message.kt
│ ├── Notification.kt
│ └── Room.kt
│
├── repository/
│ ├── UserRepository.kt
│ ├── DeferralRepository.kt
│ ├── MessagingRepository.kt
│ ├── RoomBookingRepository.kt
│ └── NotificationRepository.kt
│
└── firebase/
├── FirestoreService.kt
├── StorageService.kt
└── CloudFunctions.kt


---

## Responsibilities by Submodule

### **models/**
Contains data classes used throughout the app.

Examples:
- `User`: user profiles with roles (Student, Instructor, Admin, Proctor)
- `DeferralRequest`: primary entity in the system
- `Message`: messaging object for internal communication
- `Notification`: notification object from FCM
- `Room`: room booking metadata

Models should remain **simple Kotlin data classes** with no logic.

---

### **repository/**
Implements the Repository Pattern.

Each repository wraps all data operations:

- `UserRepository`: fetch user roles, profile data
- `DeferralRepository`: create/update deferral requests
- `MessagingRepository`: send/read messages
- `RoomBookingRepository`: request room availability & bookings
- `NotificationRepository`: manage FCM tokens and device subscriptions

Repositories must:
- only communicate with `firebase/` services
- return suspending functions (Kotlin coroutines)
- use clean error handling (Result<T>)

---

### **firebase/**
Low-level wrappers for Firebase SDK calls.

- `FirestoreService`: CRUD operations for collections
- `StorageService`: handles attachments (if any)
- `CloudFunctions`: wrapper for calling Firebase Cloud Functions

These classes should contain **no UI logic** or Android dependencies.

---

## Development Notes

- ViewModels must only talk to repositories — not Firebase.
- Repositories must handle input validation, not services.
- Models must remain *pure* data containers.
- Avoid circular dependencies (logic ↔ data must remain separated).
- All Firebase paths and document structures should be consistent with UML.

---

