from curses.ascii import ESC
import pygame
from pygame.locals import *
import pygame.event

if __name__ == "__main__":
    pygame.init()

    surface = pygame.display.set_mode((1000,500))
    surface.fill((92,25,84))
    pygame.display.flip()

    running = true
    
    while running:
        for event in pygame.event.get():
            if event.type == QUIT :
                if event.key == K_ESCAPE:
                    surface.pygame.mixer.quit()


    
    
