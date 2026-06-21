import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
  stages: [
    { duration: "30s", target: 10 },
    { duration: "1m", target: 50 },
    { duration: "30s", target: 0 },
  ],
  thresholds: {
    http_req_failed: ["rate<0.05"],
    http_req_duration: ["p(95)<3000"],
  },
};

export default function () {
  const loginRes = http.post(
    "http://localhost:8080/auth/login",
    JSON.stringify({ username: "admin", password: "admin123" }),
    { headers: { "Content-Type": "application/json" } }
  );
  const okLogin = check(loginRes, {
    "login 200": (r) => r.status === 200,
  });
  if (!okLogin) return;

  let token;
  try {
    token = loginRes.json("token");
  } catch (e) {
    return;
  }

  const payload = JSON.stringify({
    name: `Load-${__VU}-${__ITER}`,
    quantity: 1,
    price: 10,
  });

  const createRes = http.post(
    "http://localhost:8080/api/inventory/products",
    payload,
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );

  check(createRes, {
    "create 200": (r) => r.status === 200,
  });

  sleep(1);
}
