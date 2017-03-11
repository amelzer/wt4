
export const setHoverStyle = (index) => {
  return {
    type: 'SET_HOVER_STYLE',
    index
  }
}

export const resetHoverStyle = () => {
  return {
    type: 'RESET_HOVER_STYLE'
  }
}


export const chooseColumn = (index) => {
  return {
    type: 'CHOOSE_COLUMN'
  }
}
