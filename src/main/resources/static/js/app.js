const BASE_URL = "http://localhost:8080";


function register() {

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(BASE_URL + "/api/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            email: email,
            password: password
        })
    })
    .then(res => res.text())
    .then(msg => {
        window.location.href = "login.html";
    })
    .catch(err => console.error(err));
}

// 🔐 CHECK LOGIN
function checkAuth() {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Please login first");
        window.location.href = "login.html";
    }
}

// 🔐 LOGIN
function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // 🔥 VALIDATION
    if (!email || !password) {
        alert("Please enter email and password");
        return;
    }

    fetch(BASE_URL + "/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Invalid credentials");
        }
        return res.text();
    })
    .then(token => {

        // 🔥 EXTRA SAFETY
        if (!token || token.length < 20) {
            throw new Error("Invalid token received");
        }

        localStorage.setItem("token", token);

        window.location.href = "products.html";
    })
    .catch(err => {
        alert(err.message);
    });
}

// 🔍 GET ROLE FROM TOKEN
function getUserRole() {
    const token = localStorage.getItem("token");
    if (!token) return null;

    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role;
}

function isAdmin() {
    return getUserRole() === "ADMIN";
}

// 🛍️ LOAD PRODUCTS
function loadProducts() {

    const token = localStorage.getItem("token");

    if (isAdmin()) {
        document.getElementById("adminSection").style.display = "block";
    }

    fetch(BASE_URL + "/api/products", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Unauthorized");
        return res.json();
    })
    .then(data => {

        let html = "";

        data.forEach(p => {

            html += `
                <div class="card">
                    <h3>${p.name}</h3>
                    <p>${p.description}</p>
                    <p class="price">₹ ${p.price}</p>

                    <button onclick="addToCart(${p.id})">Add to Cart</button>

                    ${isAdmin() ? `
                        <button style="background:red; margin-top:10px;"
                                onclick="deleteProduct(${p.id})">
                            ❌ Delete
                        </button>
                    ` : ""}
                </div>
            `;
        });

        document.getElementById("products").innerHTML = html;
    })
    .catch(err => {
        alert("Session expired. Login again.");
        window.location.href = "login.html";
    });
}


function deleteProduct(productId) {

    const token = localStorage.getItem("token");

    if (!confirm("Are you sure you want to delete this product?")) {
        return;
    }

    fetch(BASE_URL + "/api/products/" + productId, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Delete failed (Admin only)");
        return res.text();
    })
    .then(msg => {
        alert("✅ Product deleted");
        loadProducts(); // 🔥 refresh UI
    })
    .catch(err => alert(err.message));
}

// 🛒 ADD TO CART (SECURE)
function addToCart(productId) {

    const token = localStorage.getItem("token");

    fetch(`${BASE_URL}/api/cart/add-secure?productId=${productId}&quantity=1`, {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.text())
    .then(msg => alert(msg));
}

// 🛒 LOAD CART
function loadCart() {

    const token = localStorage.getItem("token");

    fetch(BASE_URL + "/api/cart/my", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(data => {

        let rows = "";
        let total = 0;

        data.items.forEach(item => {

            total += item.price * item.quantity;

            rows += `
                <tr>
                    <td>${item.productName}</td>
                    <td>₹ ${item.price}</td>
                    <td>${item.quantity}</td>
                    <td>
                        <button onclick="removeFromCart(${item.productId})">Remove</button>
                    </td>
                </tr>
            `;
        });

        document.querySelector("#cartTable tbody").innerHTML = rows;
        document.getElementById("total").innerText = "Total: ₹ " + total;
    });
}

// ❌ REMOVE ITEM
function removeFromCart(productId) {

    const token = localStorage.getItem("token");

    fetch(`${BASE_URL}/api/cart/remove-secure?productId=${productId}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        loadCart();
    });
}

// 📦 PLACE ORDER
function placeOrder() {

    const token = localStorage.getItem("token");

    fetch(BASE_URL + "/api/orders/place", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.text())
    .then(() => {
        alert("✅ Order placed successfully!");
        window.location.href = "orders.html";
    });
}

// 📦 LOAD ORDERS
function loadOrders() {

    const token = localStorage.getItem("token");

    fetch(BASE_URL + "/api/orders/my", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(data => {

        if (!data || data.length === 0) {
            document.getElementById("orders").innerHTML =
                "<h3>No orders found</h3>";
            return;
        }

        let html = "";

        data.forEach(order => {

            html += `
                <div class="order-card">

                    <div class="order-header">
                        <h3>Order #${order.id}</h3>
                        <span class="status">${order.status}</span>
                    </div>

                    <p class="order-total">Total: ₹ ${order.totalAmount}</p>

                    <div class="order-items">
            `;

            order.items.forEach(item => {
                html += `
                    <div class="item">
                        <span>${item.productName}</span>
                        <span>₹ ${item.price} × ${item.quantity}</span>
                    </div>
                `;
            });

            html += `</div></div>`;
        });

        document.getElementById("orders").innerHTML = html;
    });
}

// 🔁 NAVIGATION
function goToProducts() { window.location.href = "products.html"; }
function goToCart() { window.location.href = "cart.html"; }
function goToOrders() { window.location.href = "orders.html"; }

// 🚪 LOGOUT
function logout() {
    localStorage.removeItem("token");
    alert("Logged out");
    window.location.href = "login.html";
}


function searchProducts() {

    const keyword = document.getElementById("searchInput").value.toLowerCase();

    const cards = document.querySelectorAll(".card");

    cards.forEach(card => {

        const text = card.innerText.toLowerCase();

        if (text.includes(keyword)) {
            card.style.display = "block";
        } else {
            card.style.display = "none";
        }
    });
}


function toggleAddForm() {

    const form = document.getElementById("addProductForm");

    if (form.style.display === "none") {
        form.style.display = "block";
    } else {
        form.style.display = "none";
    }
}

function addProduct() {

    const token = localStorage.getItem("token");

    const name = document.getElementById("pName").value;
    const description = document.getElementById("pDesc").value;
    const price = document.getElementById("pPrice").value;

    fetch(BASE_URL + "/api/products", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
            name: name,
            description: description,
            price: price
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Only admin can add product");
        return res.json();
    })
    .then(data => {
        alert("✅ Product added successfully!");
        loadProducts(); // refresh list
    })
    .catch(err => alert(err.message));
}