import { createSlice } from '@reduxjs/toolkit'

export const userIdSlice = createSlice({
  name: "userId",
  initialState: {
    id: 0,
  },
  reducers: {
    changeCurrentUserId: (state, action) => {
      // Redux Toolkit allows us to write "mutating" logic in reducers. It
      // doesn't actually mutate the state because it uses the Immer library,
      // which detects changes to a "draft state" and produces a brand new
      // immutable state based off those changes
      state.id = action.payload;
    },
  },
})

// Action creators are generated for each case reducer function
export const { changeCurrentUserId } = userIdSlice.actions

export default userIdSlice.reducer
