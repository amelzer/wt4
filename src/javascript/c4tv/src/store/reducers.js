import { combineReducers } from 'redux';


function gameState(state={
  topView: Array(7).fill(''),
  hoveredColumn: undefined,
  frontView: Array(7).fill(Array(7).fill(''))
}, action){
  switch(action.type){
    case 'SET_HOVER_STYLE':
      return {
        ...state,
        hoveredColumn: action.index
      }
    case 'RESET_HOVER_STYLE':
      return {
        ...state,
        hoveredColumn: undefined
      }
    default:
      return state;
  }
}

function gameSettings(state={
  numberOfRows: 7,
  numberOfColumns: 7,
  canvasWidth: 800,
  canvasHeight: 500
}, action){
  switch(action.type){
    default:
      return state;
  }
}

export default combineReducers({
  gameState,
  gameSettings
});
