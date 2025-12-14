import axiosInstance from "../api/api.js";

export const getCart = async () => {
  const response = await axiosInstance.get("/cart");
  return response.data;
};

export const addToCartService = async (productId, quantity) => {
  const response = await axiosInstance.post("/cart/add", {
    productId,
    quantity,
  });
  return response.data;
};

export const removeFromCartService = async (cartItemId) => {
  const response = await axiosInstance.delete(`/cart/remove/${cartItemId}`);
  return response.data;
};

export const clearCartService = async () => {
  const response = await axiosInstance.delete("/cart/clear");
  return response.data;
};
