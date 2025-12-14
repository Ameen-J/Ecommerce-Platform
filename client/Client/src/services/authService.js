import axiosInstance from "../api/api";

export const loginUser = async (credentials) => {
  const response = await axiosInstance.post("/auth/login", credentials);
  console.log(response);
  return response.data.token;
};

export const registerUser = async (userData) => {
  const response = await axiosInstance.post("/auth/register", userData);
  console.log(response);
  return response.data.token;
};

export const getCurrentUser = async () => {
  const response = await axiosInstance.get("/me");
  return response.data;
};
