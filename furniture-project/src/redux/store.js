import { configureStore } from '@reduxjs/toolkit'
import cartReducer from "./cart";
import userIdReducer from "./user";

export default configureStore({
  reducer: {
    cart: cartReducer,
    userId: userIdReducer
  },
})
