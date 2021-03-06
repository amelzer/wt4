import { combineReducers } from 'redux';


function gameState(state={
  topView: Array(7).fill(''),
  hoveredColumn: undefined,
  //frontView: Array(7).fill(Array(7).fill('')),
  frontView: 0,
  currentPlayer: 0
}, action){
  //console.log('ACTION!', action);
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
    case 'CHOOSE_COLUMN_START':
      return {
        ...state,
        loading: true
      }
    case 'CHOOSE_COLUMN_SUCCESS':
      console.log('column reducer frontView', action.frontView);
      let topView = action.topView.map((row) => {
        return row.player
      })
      return {
        ...state,
        topView,
        currentPlayer: (state.currentPlayer+1)%2,
        frontView: action.frontView,
        loading: false
      }
    default:
      return state;
  }
}

function gameSettings(state={
  numberOfRows: 7,
  numberOfColumns: 7,
  canvasWidth: 800,
  canvasHeight: 1900,
  activePlayer: 1,
  players: [
    {
      id: '1',
      name: 'Player 1',
      color: '#721551',
      hoverColor: '#9A427B'
    },
    {
      id: '2',
      name: 'Player 2',
      color: '#6B8C1A',
      hoverColor: '#BCD67B'
    }
  ]
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
