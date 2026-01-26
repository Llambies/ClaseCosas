from pynput.mouse import Button, Controller
import time
import keyboard

mouse = Controller()

def autoclicker(x, y, clicks=10, interval=0.5):
    for i in range(clicks):
        mouse.position = (x, y)  # Mover ratón
        mouse.click(Button.left, 1)  # Clic izquierdo
        time.sleep(interval)
        print(f"Clic {i+1} realizado")

if __name__ == "__main__":
    
    # Send click while holdin the space key
    while True:
        if keyboard.is_pressed('w'): 
            mouse.click(Button.left, 1)
            time.sleep(0.02)
        else:
            time.sleep(0.02)
    