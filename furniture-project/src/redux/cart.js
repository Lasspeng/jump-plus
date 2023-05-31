import { createSlice } from '@reduxjs/toolkit'

export const cartSlice = createSlice({
  name: "cart",
  initialState: {
    value: [],
  },
  reducers: {
    addToCart: (state, action) => {
      // Redux Toolkit allows us to write "mutating" logic in reducers. It
      // doesn't actually mutate the state because it uses the Immer library,
      // which detects changes to a "draft state" and produces a brand new
      // immutable state based off those changes
      
      state.value.push(action.payload);
    },
    removeFromCart: (state, action) => {
      for (let i = 0; i < state.value.length; i++) {
        if (state.value[i].name === action.payload) {
            state.value.splice(i, 1);
        }
      }  
    },
    emptyCart: (state) => {
      state.value = [];
    },
  },
})

// Action creators are generated for each case reducer function
export const { addToCart, removeFromCart, emptyCart } = cartSlice.actions

export default cartSlice.reducer
