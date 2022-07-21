using Acacia_Builder;
using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using System;
using static GameStateManager;

public class MenuState : GameState {

    protected Texture2D memberImg;
    protected Texture2D BarryBolin;
    protected Texture2D bulletIcon;
    protected Texture2D bullet;
    protected Texture2D speedLines;

    public int difSurge = 300;
    public int difSurgeTime = 300;
    public int bulletSpawnTimer = 0;
    public int ammo = 100000000;
    public int ammoTimer = 0;
    public int babyAmmoTimer = 0;
    public int lives = 2;
    public int screenSizeY = 400;
    public int screenCross = 0;
    public int speedLinesTimer;

    public static Color bgmCol = Color.CornflowerBlue;

    Random rnd = new Random();
    Entity barry;

    public void MakePlatform(Vector2 pos)
    {

        Entity member = e1.CreateEntity();
        member.AddComponent(new TextureComponent());
        member.AddComponent(new TransformComponent());
        member.AddComponent(new RectangleComponent());
        member.AddComponent(new SquareComponent());
        //member.GetComponent<TransformComponent>().position = new Vector2(rnd.Next(500), rnd.Next(500));
        member.GetComponent<TransformComponent>().position = pos;
        member.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 32, 32);
        member.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
        member.GetComponent<TextureComponent>().texture = memberImg;
        

    }

    public void MakeNonStickPlatform(Vector2 pos)
    {

        Entity member = e1.CreateEntity();
        member.AddComponent(new TextureComponent());
        member.AddComponent(new TransformComponent());
        member.AddComponent(new RectangleComponent());
        //member.GetComponent<TransformComponent>().position = new Vector2(rnd.Next(500), rnd.Next(500));
        member.GetComponent<TransformComponent>().position = pos;
        member.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 32, 32);
        member.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
        member.GetComponent<TextureComponent>().texture = memberImg;


    }

    public void MakeBulletIcon()
    {
        Vector2 pos = new Vector2(rnd.Next(1300), rnd.Next(100,300));
        Entity entity = e1.CreateEntity();
        entity.AddComponent(new TransformComponent());
        entity.AddComponent(new TextureComponent());
        entity.AddComponent(new RectangleComponent());
        entity.AddComponent(new bulletIconComponent());
        entity.GetComponent<TransformComponent>().position = pos;
        entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 32, 32);
        entity.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
        entity.GetComponent<TextureComponent>().texture = bulletIcon;

    }

    public void MakeBullet(Vector2 pos, Vector2 dir)
    {
        Entity entity = e1.CreateEntity();
        entity.AddComponent(new TransformComponent());
        entity.AddComponent(new TextureComponent());
        entity.AddComponent(new RectangleComponent());
        entity.AddComponent(new BulletComponent());
        entity.GetComponent<TransformComponent>().position = pos;
        dir.Normalize();
        entity.GetComponent<TransformComponent>().dx += dir.X * 5;
        entity.GetComponent<TransformComponent>().dy += dir.Y * 5;
        entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 32, 32);
        entity.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
        entity.GetComponent<TextureComponent>().texture = bullet;
    }

    public void MakeBabyBullet(Vector2 pos)
    {
        Entity entity = e1.CreateEntity();
        entity.AddComponent(new TransformComponent());
        entity.AddComponent(new TextureComponent());
        entity.AddComponent(new RectangleComponent());
        entity.AddComponent(new BabyBulletComponent());
        entity.GetComponent<TransformComponent>().position = pos;
        entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X + rnd.Next(-5,5), (int)pos.Y + rnd.Next(-5,5), 16, 16);
        entity.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
        entity.GetComponent<TextureComponent>().texture = bullet;
        entity.GetComponent<BabyBulletComponent>().itsPlace = new Vector2(rnd.Next(-15, 15), rnd.Next(-15, 15));
    }

    public void MakeSpeedLines(Vector2 pos, float sl)
    {
        Entity entity = e1.CreateEntity();
        entity.AddComponent(new TransformComponent());
        entity.AddComponent(new TextureComponent());
        entity.AddComponent(new RectangleComponent());
        entity.GetComponent<TransformComponent>().position = pos;
        entity.GetComponent<TransformComponent>().dx = sl * -1;
        entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 16, 2);
        entity.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
        entity.GetComponent<TextureComponent>().texture = speedLines;
    }

    Entity MakeBarry()
    {
        Entity barry = e1.CreateEntity();
        barry.AddComponent(new TextureComponent());
        barry.AddComponent(new TransformComponent());
        barry.AddComponent(new RectangleComponent());
        barry.GetComponent<TransformComponent>().position = new Vector2(0,200);
        barry.GetComponent<TransformComponent>().dx = 3;
        barry.GetComponent<RectangleComponent>().Rect = new Rectangle(0, 300, 32, 32);
        barry.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
        barry.GetComponent<TextureComponent>().texture = BarryBolin;
        return barry;
    }


    public MenuState(Game1 game, GameStateManager gsm):base(game,gsm)
    {

    }

    
    public override void init(){
        base.init();


        memberImg = Content.Load<Texture2D>("member");
        BarryBolin = Content.Load<Texture2D>("BarryBolin");
        bulletIcon = Content.Load<Texture2D>("bulleticon");
        bullet = Content.Load<Texture2D>("bullet");
        speedLines = Content.Load<Texture2D>("speedlines");

        

        barry = MakeBarry();


        for (int i = 0; i < 41; i++)
        {
            MakeNonStickPlatform(new Vector2(i * 32, screenSizeY-32));
        }


        //SpriteFont font = Content.Load<SpriteFont>("font");
        //Entity nextDayButton = e1.CreateEntity();
        //nextDayButton.AddComponent(new ClickableComponent());
        //nextDayButton.AddComponent(new TextComponent());
        //nextDayButton.AddComponent(new TransformComponent());
        //nextDayButton.GetComponent<TransformComponent>().position = new Vector2(300, 300);
        //nextDayButton.GetComponent<TextComponent>().font = font;
        //nextDayButton.GetComponent<TextComponent>().str = "Next Day";
        //nextDayButton.GetComponent<TextComponent>().normColor = Color.Black;
        //nextDayButton.GetComponent<TextComponent>().color = Color.Black;
        //nextDayButton.GetComponent<ClickableComponent>().action = MakeMember;

    }

    public void loadContent()
    {

    }

    float accel = (float) .8;
    public override void update(GameTime gameTime){
        draw();
        handleInput();

        difSurge++;
        bulletSpawnTimer++;
        speedLinesTimer++;
        if (ammo != 100000000)
        {
            ammoTimer++;
            babyAmmoTimer++;

            if (babyAmmoTimer >= ammo)
            {
                babyAmmoTimer = 0;
                MakeBabyBullet(new Vector2(rnd.Next(1300-32), rnd.Next(screenSizeY-32)));

            }
        }

        if(bulletSpawnTimer >= 500)
        {
            MakeBulletIcon();
            bulletSpawnTimer = 0;
        }

        if (difSurge >= difSurgeTime)
        {
            MakePlatform(new Vector2(rnd.Next(200,1300), rnd.Next(200,screenSizeY-48)));
            
            difSurge -= difSurgeTime;
        }

        if(speedLinesTimer + screenCross >= 5)
        {
            //MakeSpeedLines(new Vector2(barry.GetComponent<TransformComponent>().position.X, barry.GetComponent<TransformComponent>().position.Y + rnd.Next(32)), barry.GetComponent<TransformComponent>().dx);
            speedLinesTimer = 0;
        }

        barry.GetComponent<TransformComponent>().dy += (float) 1.2;
        

        if(barry.GetComponent<TransformComponent>().position.Y > screenSizeY-64)
        {
            barry.GetComponent<TransformComponent>().dy = 0;
            barry.GetComponent<TransformComponent>().position.Y = screenSizeY-64;
        }

        if(barry.GetComponent<TransformComponent>().position.X >= 1300)
        {
            barry.GetComponent<TransformComponent>().position.X = 0;
            screenCross++;
            if (barry.GetComponent<TransformComponent>().dx >= 0)
            {
                barry.GetComponent<TransformComponent>().dx += accel;
            }
            else
            {
                barry.GetComponent<TransformComponent>().dx -= accel;
            }
        }

        if(barry.GetComponent<TransformComponent>().position.X < 0)
        {
            if (lives > 0)
            {
                barry.GetComponent<TransformComponent>().dx *= -1;
                lives--;
                
                if (bgmCol == Color.CornflowerBlue)
                {
                    bgmCol = Color.Yellow;
                }
                else
                {
                    bgmCol = Color.Crimson;
                }
            }

            else
            {
                e1.DestroyEntity(barry);
                game.quit();
            }
        }

        ImmutableList<Entity> squareFriends = e1.GetEntitiesFor(Family.All(typeof(SquareComponent)).Get());
        for (int i = 0; i < squareFriends.Count; i++)
        {

            if (barry.GetComponent<RectangleComponent>().Rect.Intersects(squareFriends[i].GetComponent<RectangleComponent>().Rect))
            {

                barry.GetComponent<TransformComponent>().dx *= -1;
            }

            ImmutableList<Entity> bullets = e1.GetEntitiesFor(Family.All(typeof(BulletComponent)).Get());
            for (int j = 0; j < bullets.Count; j++)
            {
                if (bullets[j].GetComponent<RectangleComponent>().Rect.Intersects(squareFriends[i].GetComponent<RectangleComponent>().Rect) && squareFriends.Count > i)
                {
                    e1.DestroyEntity(bullets[j]);
                    e1.DestroyEntity(squareFriends[i]);
                    break;
                }
            }
        }

        ImmutableList<Entity> bulletIconEnts = e1.GetEntitiesFor(Family.All(typeof(bulletIconComponent)).Get());
        for (int i = 0; i < bulletIconEnts.Count; i++)
        {

            if (barry.GetComponent<RectangleComponent>().Rect.Intersects(bulletIconEnts[i].GetComponent<RectangleComponent>().Rect))
            {
                e1.DestroyEntity(bulletIconEnts[i]);
                if(ammo == 100000000)
                {
                    ammo = 140;
                }
                else
                {
                    ammo -= 2;
                }
            }
        }

        ImmutableList<Entity> babyBulletEnt = e1.GetEntitiesFor(Family.All(typeof(BabyBulletComponent)).Get());
        for (int i = 0; i < babyBulletEnt.Count; i++)
        {
            Vector2 dir = barry.GetComponent<TransformComponent>().position - babyBulletEnt[i].GetComponent<TransformComponent>().position + babyBulletEnt[i].GetComponent<BabyBulletComponent>().itsPlace;
            dir.Normalize();
            babyBulletEnt[i].GetComponent<TransformComponent>().position += dir * 5;
        }
    }

    public void draw(){

        
        

    }


    MouseState oldState;

    public override void handleInput() {
        KeyboardState keyState = Keyboard.GetState();
        if (keyState.IsKeyDown(Keys.Space) && barry.GetComponent<TransformComponent>().position.Y == screenSizeY-64) {
                barry.GetComponent<TransformComponent>().dy -= 29;
                MakePlatform(new Vector2(rnd.Next(200,1300),rnd.Next(240)));
            }

        MouseState newState = Mouse.GetState();

        if (newState.LeftButton == ButtonState.Pressed && oldState.LeftButton == ButtonState.Released && ammoTimer >= ammo && barry.GetComponent<TransformComponent>().position.Y < screenSizeY - 64)
        {
            Vector2 mousePos= new Vector2(newState.X, newState.Y);
            MakeBullet(barry.GetComponent<TransformComponent>().position, mousePos - barry.GetComponent<TransformComponent>().position);
            ammoTimer -= ammo;

            ImmutableList<Entity> babyBulletEnt = e1.GetEntitiesFor(Family.All(typeof(BabyBulletComponent)).Get());
            if (babyBulletEnt.Count > 0)
            {
                e1.DestroyEntity(babyBulletEnt[0]);
            }
        }

        oldState = newState; // t

        //if (keyState.IsKeyDown(Keys.Left))
        //{
        //    if (barry.GetComponent<TransformComponent>().dx >= 0)
        //    {
        //        barry.GetComponent<TransformComponent>().dx *= -1;
        //    }

        //}
        //if (keyState.IsKeyDown(Keys.Right))
        //{
        //    if (barry.GetComponent<TransformComponent>().dx <= 0)
        //    {
        //        barry.GetComponent<TransformComponent>().dx *= -1;
        //    }
        //}

    }

    private void select(){

    }

}

