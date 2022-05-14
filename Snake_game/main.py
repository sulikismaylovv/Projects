from curses.ascii import ESC
import pygame
from pygame.locals import *
import pygame.event
import time

class Game:
    def __init__(self) -> None:
        pygame.init()
        #initialize surface
        self.surface = pygame.display.set_mode((500,500))
        self.surface.fill((92,25,84))
        self.snake = Snake(self.surface)
        self.snake.draw()

    def run(self):
        running = True

        while running:
            for event in pygame.event.get():
                if event.type == KEYDOWN:
                    if event.key == K_ESCAPE:
                        running = False
                    if event.key == K_LEFT or event.key == K_a:
                        self.snake.move_left()

                    if event.key == K_RIGHT or event.key == K_d:
                        self.snake.move_right()

                    if event.key == K_UP or event.key == K_w:
                        self.snake.move_up()

                    if event.key == K_DOWN or event.key == K_s:
                        self.snake.move_down()

                elif event.type == QUIT:
                    running = False

            self.snake.walk()
            time.sleep(0.1)



class Snake:
    def __init__(self , screen):
        self.screen = screen
        #initialize block
        self.block = pygame.image.load("/Users/suleymanismaylov/VSCode/Projects/Snake_game/resources/block.jpg").convert()
        self.x = 100 
        self.y = 100
        self.direction = "down"

    #methods to move snake
    def move_left(self):
        self.direction = 'left'

    def move_right(self):
        self.direction = 'right'

    def move_up(self):
        self.direction = 'up'

    def move_down(self):
        self.direction = 'down'
    
    def walk(self):
        if self.direction == 'left':
            self.x -= 10
        if self.direction == 'right':
            self.x += 10
        if self.direction == 'up':
            self.y -= 10
        if self.direction == 'down':
            self.y += 10

        self.draw()
    
    #clear screen and place new block   
    def draw(self):
        self.screen.fill((92,25,84))

        self.screen.blit(self.block, (self.x, self.y))
        pygame.display.flip()

    

#initialize everything
if __name__ == "__main__":
    game = Game()
    game.run()

    
    
