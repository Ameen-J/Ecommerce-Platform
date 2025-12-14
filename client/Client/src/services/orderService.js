import axiosInstance from "../api/api.js";

export const checkoutCart = async () => {
  const response = await axiosInstance.post("/cart/checkout");
  return response.data;
};

export const getUserOrders = async () => {
  const response = await axiosInstance.get("/orders/my-orders");
  return response.data;
};
