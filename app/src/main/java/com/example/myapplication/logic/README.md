# Logic Layer (Application Layer)

This directory contains the **application logic** for the Deferral Management System.  
It acts as the bridge between the UI (Jetpack Compose screens) and the Data Layer (Firebase repositories).

The logic layer contains:
- Business rules
- Validation logic
- Role-based workflows
- Messaging coordination
- Scheduling and room booking logic
- Authentication routing
- ViewModels used by the UI screens (MVVM Architecture)

---

## Folder Structure
logic/
│
├── auth/
│ ├── AuthManager.kt
│ ├── RoleRouter.kt
│ └── UserSessionManager.kt
│
├── viewmodels/
│ ├── StudentViewModel.kt
│ ├── InstructorViewModel.kt
│ ├── AdminViewModel.kt
│ └── ProctorViewModel.kt
│
├── messaging/
│ ├── MessagingManager.kt
│ ├── ChatHandler.kt
│ └── MessageFormatter.kt
│
├── notification/
│ ├── NotificationManager.kt
│ └── NotificationUtils.kt
│
├── scheduling/
│ ├── SchedulingController.kt
│ ├── RoomBookingCoordinator.kt
│ └── CalendarHelper.kt
│
└── utils/
├── Validators.kt
├── Constants.kt
└── DateUtils.kt

---

## Responsibilities by Submodule

### **auth/**
Handles the authentication flow using FirebaseAuth and controls post-login role routing.

- `AuthManager`: login, logout, token refresh
- `RoleRouter`: directs users to Student/Instructor/Admin/Proctor UI
- `UserSessionManager`: stores active user session data

---

### **viewmodels/**
Holds state and business logic for each user role (MVVM).

- `StudentViewModel`: submitting deferral forms, viewing status
- `InstructorViewModel`: handling approvals/denials
- `AdminViewModel`: scheduling, messaging, booking rooms
- `ProctorViewModel`: managing availability, receiving assigned exams

---

### **messaging/**
Internal messaging system between Admin ↔ Instructor ↔ Proctor.

- `MessagingManager`: high-level messaging operations
- `ChatHandler`: Firestore real-time syncing
- `MessageFormatter`: text formatting for UI

---

### **notification/**
Wraps Android + Firebase Cloud Messaging.

- `NotificationManager`: subscribe to topics, device tokens
- `NotificationUtils`: building local notifications

---

### **scheduling/**
Controls exam scheduling and room assignments.

- `SchedulingController`: logic for generating deferral exam schedules
- `RoomBookingCoordinator`: checks room availability
- `CalendarHelper`: date and time utilities

---

### **utils/**
Generic helpers used across the logic layer.

- `Validators`: input validation (non-empty fields, date checks, etc.)
- `Constants`: global constant values
- `DateUtils`: formatting, conversion, timestamp helpers

---

## Development Notes

- All ViewModels should communicate **only** with repositories (data layer), not Firebase directly.
- All business rules should live here, not in the UI.
- Messaging and scheduling modules must remain decoupled.
- Use dependency injection (Hilt) when this project scales.

---

