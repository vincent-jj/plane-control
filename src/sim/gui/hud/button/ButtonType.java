package sim.gui.hud.button;

import sim.engine.*;


public interface ButtonType {

        public void action(Generator generator);
         
        public ButtonType ACCELERATE_X1 = new ButtonType(){
                final static private int acceleration = 1;
                
                public void action(Generator generator){
                        this.provokeAcceleration(generator);
                        return;
                }
                private void provokeAcceleration(Generator generator){
                        /* Lancer l'acceleration du moteur de jeu et de l'affichage */
                        generator.ChangeSpeed(acceleration);
                }
                private void stopAcceleration(Generator generator){
                        generator.ChangeSpeed(1);
                }
                private void reinitializeState(Generator generator){
                        generator.ChangeSpeed(1);
                }
        };

	public ButtonType ACCELERATE_X2 = new ButtonType(){
		final static private int acceleration = 2;
                
                public void action(Generator generator){
                        this.provokeAcceleration(generator);
                        return;
                }
		private void provokeAcceleration(Generator generator){
			/* Lancer l'acceleration du moteur de jeu et de l'affichage */
			generator.ChangeSpeed(acceleration);
		}
		private void stopAcceleration(Generator generator){
			generator.ChangeSpeed(1);
		}
		private void reinitialize_state(Generator generator){
			generator.ChangeSpeed(1);
		}
	};
	
	public ButtonType ACCELERATE_X4 = new ButtonType(){
		final static private int acceleration = 4;
                private boolean actionState = false;
                
                public void action(Generator generator){
                        this.provokeAcceleration(generator);
                        return;
                }		
                private void provokeAcceleration(Generator generator){
			/* Lancer l'acc�l�ration du moteur de jeu et de l'affichage */
			generator.ChangeSpeed(acceleration);
			return;
		}
		private void stopAcceleration(Generator generator){
			/* Stopper l'acceleration du moteur en le remettant � 1 */
			generator.ChangeSpeed(1);
			return;
		}
		private void reinitialize_state(Generator generator){
			generator.ChangeSpeed(1);
		}
	};
	
	public ButtonType ACCELERATE_X8 = new ButtonType(){
		final static private int acceleration = 8;
		private boolean actionState = false;
                
                public void action(Generator generator){
                        this.provokeAcceleration(generator);
                        return;
                }
		
		private void provokeAcceleration(Generator generator){
			/* Lancer l'acc�l�ration du moteur de jeu et de l'affichage */
			generator.ChangeSpeed(acceleration);
			return;
		}
		private void stopAcceleration(Generator generator){
			/* Stopper l'acceleration du moteur en le remettant � 1 */
			generator.ChangeSpeed(1);
			return;
		}
		private void reinitialize_state(Generator generator){
			generator.ChangeSpeed(1);
		}
	};
	
	public ButtonType TRANSLATION_FR = new ButtonType(){
                public void action(Generator generator){
		    this.changeLanguage();
                    return;
                }
		private void changeLanguage(){
		    /* Faire un affichage du fait que le jeu est pass� en fran�ais ??? */
		    return;
		}
	    };
	
	public ButtonType TRANSLATION_EN = new ButtonType(){
		public void action(Generator generator){
		    this.changeLanguage();
		    return;
		}
		private void changeLanguage(){
			/* Faire un affichage du fait que le jeu est pass� en fran�ais ??? */
			return;
		}
	};
	/*
	public ButtonType LAUNCH_ACTION = new ButtonType(){
		public void action(Generator generator){
		    this.sendCommandToEngine(String command , Generator generator);
		    return;
		}
		private void sendCommandToEngine(String command , Generator generator){
			parserEngine(command);
			return;
		}
	};
	*/
	public ButtonType ACTIVATE_CMD_AUDIO = new ButtonType(){
		private boolean vocalAnalysisActive = false;
		
		public void action(Generator generator){
		    if(!this.vocalAnalysisActive)
			this.activateVocalAnalysis();
		    else 
			this.stopVocalAnalysis();
		        this.vocalAnalysisActive = !this.vocalAnalysisActive;
		    return;
		}
		    
		private void activateVocalAnalysis(){
			/* 
			 * setVocalAnalysis(true); 
			 */
			return;
		}
		private void stopVocalAnalysis(){
			/*
			 * setVocalAnalysis(false);
			 */
			return;
		}
		private void reinitializeState(){
			/*
			 * setVocalAnalysis(false);
			 */
			return;
		}	
	};
	
	
	public ButtonType PLAY_PAUSE = new ButtonType(){
		private boolean isInPause = false;
		
		public void action (Generator generator) {
		    this.changeStatus(generator);
		    return;
		}
		
		private void changeStatus(Generator generator){
			this.isInPause = !this.isInPause;
			return;
		}
		private void reinitializeState(Generator generator){
			this.isInPause = false;
		}
	};
}
