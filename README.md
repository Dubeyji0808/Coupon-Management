Here is a **clean, professional, submission-ready README.md** for your **Coupon Management** assignment.

You can **copy–paste** this directly into your GitHub repository.

---

# 📘 Coupon Management System — Backend Assignment

A simple, scalable, rule-based **Coupon Management System** built with **Java Spring Boot**.
This service allows you to:

* Create coupons with eligibility rules
* Store them in-memory
* Evaluate all coupons against a given user + cart
* Return the **best applicable coupon** based on discount amount
* Enforce date validity, usage limits, and eligibility filters

This project is built strictly according to the assignment requirements.

---

# 🚀 Features Implemented

### ✅ **Create Coupon API**

* Unique coupon code validation
* FLAT or PERCENT discounts
* Max discount cap for PERCENT types
* Eligibility rules
* Usage limit per user
* Date validation (startDate–endDate)

### ✅ **Best Coupon API**

* Validates:

    * Coupon date window
    * Usage limit per user
    * User eligibility rules
    * Cart eligibility rules
* Computes discount:

    * FLAT discount
    * PERCENT discount (with max cap)
* Deterministic tie-breaking:

    1. Highest discount
    2. Earliest endDate
    3. Alphabetically smallest coupon code
* Usage tracking included

### ✅ **In-Memory Storage**

* No database required
* Data resets on app restart

### ✅ **Global Exception Handling**

* Business rule violations
* Invalid inputs
* JSON error responses

### ✅ **Postman-tested**

All required test cases validated (see README bottom).

---

# 🏗️ Tech Stack

**Backend:**

* Java 17
* Spring Boot 3.x
* Spring Web
* Lombok
* Validation API

**Other:**

* In-memory ConcurrentHashMap
* Postman for API testing

---

# 📂 Project Structure

```
src/main/java/com/example/coupon
│
├── controller
│     └── CouponController.java
│
├── service
│     └── CouponService.java
│
├── store
│     ├── CouponStore.java
│     └── UsageTracker.java
│
├── model
│     ├── Coupon.java
│     ├── Eligibility.java
│     ├── UserContext.java
│     ├── Cart.java
│     ├── CartItem.java
│     ├── request
│     │      ├── CreateCouponRequest.java
│     │      └── BestCouponRequest.java
│     └── response
│            └── BestCouponResponse.java
│
└── exception
      ├── CouponException.java
      ├── ErrorResponse.java
      └── GlobalExceptionHandler.java
```

---

# ▶️ How to Run Locally

### **Prerequisites**

* Java 17+
* Maven 3+
* Postman (for testing)

### **Steps**

```bash
git clone <your-repo-url>
cd coupon-management
mvn clean install
mvn spring-boot:run
```

Server runs at:

```
http://localhost:8080
```

---

# 📌 API Endpoints

## 1️⃣ **Create Coupon**

```
POST /coupons
```

### Sample Request

```json
{
  "code": "WELCOME100",
  "description": "₹100 off",
  "discountType": "FLAT",
  "discountValue": 100,
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2025-12-31T23:59:59",
  "usageLimitPerUser": 1,
  "eligibility": {
    "allowedUserTiers": ["NEW"],
    "firstOrderOnly": true,
    "allowedCountries": ["IN"],
    "minCartValue": 500,
    "applicableCategories": ["electronics"]
  }
}
```

---

## 2️⃣ **Get All Coupons**

```
GET /coupons
```

---

## 3️⃣ **Best Coupon API**

```
POST /coupons/best
```

### Sample Request

```json
{
  "user": {
    "userId": "u123",
    "userTier": "NEW",
    "country": "IN",
    "lifetimeSpend": 1200,
    "ordersPlaced": 0
  },
  "cart": {
    "items": [
      {
        "productId": "p1",
        "category": "electronics",
        "unitPrice": 1500,
        "quantity": 1
      }
    ]
  }
}
```

### Sample Response

```json
{
  "coupon": {
    "code": "ELEC10"
  },
  "discount": 150.0
}
```

---

# ❗ Error Example

### Duplicate coupon code:

```json
{
  "timestamp": "2025-11-20T11:18:29",
  "message": "Coupon code already exists: WELCOME100",
  "details": "Business rule violation"
}
```

---

# 🧪 Postman Testing Scenarios (All Tested)

### ✔ Create Coupons

### ✔ Duplicate Code

### ✔ Get All Coupons

### ✔ Best Coupon Selection

### ✔ First-Order Only Rule

### ✔ Usage Limit Per User

### ✔ Category Mismatch

### ✔ Min Cart Value

### ✔ Multiple Items

### ✔ Percent Discount Cap

### ✔ Tie Breaking Rules

### ✔ Error Handling

(You can include Postman collection in repo if needed)

---

# 🔗 Submission Info

Include these in assignment form:

* **Name**
* **GitHub Repository Link**
* **Live Deployment Link (optional)**
* **Tech Stack Used**
* **Prompts used (AI note)**

---

# 🧠 AI Usage Note (Mandatory for Assignment)

This project was developed with assistance from ChatGPT for:

* Code structure planning
* Building models and DTOs
* Writing eligibility logic
* Writing README documentation

All code was thoroughly reviewed and tested manually.

---

# 🎉 Done!

Your README is now ready for submission.
If you want, I can also generate:

✅ Perfect formatted GitHub README with badges
✅ Postman collection
✅ Deployment guide (Render / Railway)

Just tell me!
