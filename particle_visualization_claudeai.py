import numpy as np
import pygame
import os
from dataclasses import dataclass
from typing import List, Tuple
import colorsys
from datetime import datetime
from PIL import Image

@dataclass
class Particle:
    x: float
    y: float
    z: float
    speed: float
    color: Tuple[int, int, int]
    size: float
    
class ParticleSystem:
    def __init__(
        self,
        width: int = 800,
        height: int = 800,
        num_particles: int = 200,
        color_spectrum: str = "rainbow",
        particle_speed: float = 2.0,
        particle_size: float = 2.0,
        zoom_factor: float = 1.0
    ):
        pygame.init()
        self.width = width
        self.height = height
        self.screen = pygame.display.set_mode((width, height))
        pygame.display.set_caption("Particle Visualization")
        self.clock = pygame.time.Clock()
        self.particles = []
        self.num_particles = num_particles
        self.color_spectrum = color_spectrum
        self.particle_speed = particle_speed
        self.particle_size = particle_size
        self.zoom_factor = zoom_factor
        self.initialize_particles()
        
    def get_color(self, index: int) -> Tuple[int, int, int]:
        if self.color_spectrum == "rainbow":
            hue = (index / self.num_particles) % 1.0
            rgb = colorsys.hsv_to_rgb(hue, 1.0, 1.0)
            return tuple(int(255 * x) for x in rgb)
        elif self.color_spectrum == "white":
            return (255, 255, 255)
        else:
            return (200, 200, 255)  # Default blue-white
            
    def initialize_particles(self):
        for i in range(self.num_particles):
            particle = Particle(
                x=np.random.uniform(-1, 1),
                y=np.random.uniform(-1, 1),
                z=np.random.uniform(0, 2),
                speed=np.random.uniform(0.5, 1.5) * self.particle_speed,
                color=self.get_color(i),
                size=np.random.uniform(0.5, 1.5) * self.particle_size
            )
            self.particles.append(particle)
            
    def update_particles(self):
        for particle in self.particles:
            # Move particles toward the center while rotating
            particle.z -= particle.speed * 0.01
            
            # Add slight rotation
            angle = particle.speed * 0.02
            x = particle.x * np.cos(angle) - particle.y * np.sin(angle)
            y = particle.x * np.sin(angle) + particle.y * np.cos(angle)
            particle.x = x
            particle.y = y
            
            # Reset particles that go too far
            if particle.z <= 0:
                particle.z = 2
                particle.x = np.random.uniform(-1, 1)
                particle.y = np.random.uniform(-1, 1)
                
    def draw_particles(self):
        self.screen.fill((0, 0, 0))  # Black background
        
        for particle in self.particles:
            # Project 3D coordinates to 2D screen space
            factor = self.zoom_factor / (particle.z + 0.1)
            x = self.width // 2 + int(particle.x * factor * self.width // 2)
            y = self.height // 2 + int(particle.y * factor * self.height // 2)
            
            # Calculate size based on z-position
            size = max(1, int(particle.size * factor))
            
            # Draw particle
            if 0 <= x < self.width and 0 <= y < self.height:
                pygame.draw.circle(self.screen, particle.color, (x, y), size)

    def pygame_surface_to_pil_image(self, surface):
        """Convert a Pygame surface to a PIL Image"""
        raw_str = pygame.image.tostring(surface, 'RGB', False)
        return Image.frombytes('RGB', surface.get_size(), raw_str)
                
    def run(self, duration_seconds: int = 5, fps: int = 30, output_gif: str = "particle_visualization.gif"):
        frames = []  # Store PIL Images
        total_frames = duration_seconds * fps
        running = True
        frame = 0
        
        print(f"Starting particle visualization...")
        print(f"Press ESC to exit early")
        
        while running and frame < total_frames:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                elif event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_ESCAPE:
                        running = False
            
            self.update_particles()
            self.draw_particles()
            pygame.display.flip()
            
            # Convert Pygame surface to PIL Image and store
            pil_frame = self.pygame_surface_to_pil_image(self.screen)
            frames.append(pil_frame)
            
            frame += 1
            self.clock.tick(fps)
            
            # Show progress
            if frame % fps == 0:
                print(f"Progress: {frame}/{total_frames} frames ({frame/total_frames*100:.1f}%)")
        
        pygame.quit()
        
        # Save the GIF
        print(f"\nSaving GIF...")
        frames[0].save(
            output_gif,
            save_all=True,
            append_images=frames[1:],
            duration=int(1000/fps),  # Duration for each frame in milliseconds
            loop=0  # 0 means loop forever
        )
        print(f"Animation saved as {output_gif}")

# Example usage
if __name__ == "__main__":
    particle_system = ParticleSystem(
        width=800,
        height=800,
        num_particles=300,
        color_spectrum="rainbow",
        particle_speed=2.0,
        particle_size=2.0,
        zoom_factor=1.5
    )
    particle_system.run(duration_seconds=5, fps=30)